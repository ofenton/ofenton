package com.ofenton.cascading.hashjoin;


import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop2.Hadoop2MR1FlowConnector;
import cascading.operation.Insert;
import cascading.operation.aggregator.Count;
import cascading.operation.expression.ExpressionFunction;
import cascading.operation.regex.RegexFilter;
import cascading.operation.regex.RegexSplitGenerator;
import cascading.pipe.*;
import cascading.pipe.assembly.*;
import cascading.pipe.joiner.LeftJoin;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

import java.util.Properties;

/**
 * Created by ofenton on 03/06/2015.
 */
public class Main {

    public static void main(String[] args) {
        String docPath = args[0];
        String wcPath = args[1] + "/wc";
        String tfidfPath = args[1] + "/tfidf";
        String dotPath = args[1] + "/dot/wc.dot";
        String stopPath = args[2];

        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, Main.class);
        FlowConnector flowConnector = new Hadoop2MR1FlowConnector(properties);

        // Create source & sink taps
        Tap docTap = new Hfs(new TextDelimited(true, "\t"), docPath);
        Tap wcTap = new Hfs(new TextDelimited(true, "\t"), wcPath);

        Fields stop = new Fields("stop");
        Tap stopTap = new Hfs(new TextDelimited(stop, true, "\t"), stopPath);
        Tap tfidfTap = new Hfs(new TextDelimited(true, "\t"), tfidfPath);

        // Specify a regex to split the document into tokens
        Fields token = new Fields("token");
        Fields text = new Fields("text");
        RegexSplitGenerator splitter = new RegexSplitGenerator(token, "[ \\[\\]\\(\\),.]");
        Fields fieldSelector = new Fields("doc_id", "token");
        Pipe docPipe = new Each("token", text, splitter, fieldSelector);

        // Define ScrubFunction to clean up the tokens
        Fields scrubArguments = new Fields("doc_id", "token");
        docPipe = new Each(docPipe, scrubArguments, new ScrubFunction(scrubArguments), Fields.RESULTS);

        // Perform the left join to remove stop words, discarding rows
        // Which joined with the stop words
        Pipe stopPipe = new Pipe("stop");
        Pipe tokenPipe = new HashJoin(docPipe, token, stopPipe, stop, new LeftJoin());
        tokenPipe = new Each(tokenPipe, stop, new RegexFilter("^$"));
        tokenPipe = new Retain(tokenPipe, fieldSelector);

        // One branch of the flow tallies the token counts for term frequency (TF)
        Pipe tfPipe = new Pipe("TF", tokenPipe);
        Fields tf_count = new Fields("tf_count");
        tfPipe = new CountBy(tfPipe, new Fields("doc_id", "token"), tf_count);

        Fields tf_token = new Fields("tf_token");
        tfPipe = new Rename(tfPipe, token, tf_token);

        // One branch counts the number of documents
        Fields doc_id = new Fields("doc_id");
        Fields tally = new Fields("tally");
        Fields rhs_join = new Fields("rhs_join");
        Fields n_docs = new Fields("n_docs");
        Pipe dPipe = new Unique("D", tokenPipe, doc_id);
        dPipe = new Each(dPipe, new Insert(tally, 1), Fields.ALL);
        dPipe = new Each(dPipe, new Insert(rhs_join, 1), Fields.ALL);
        dPipe = new SumBy(dPipe, rhs_join, tally, n_docs, Long.class);

        // One branch tallies the token counts for document frequency (DF)
        Pipe dfPipe = new Unique("DF", tokenPipe, Fields.ALL);
        Fields df_count = new Fields("df_count");
        dfPipe = new CountBy(dfPipe, token, df_count);

        Fields df_token = new Fields("df_token");
        Fields lhs_join = new Fields("lhs_join");
        dfPipe = new Rename(dfPipe, token, df_token);
        dfPipe = new Each(dfPipe, new Insert(lhs_join, 1), Fields.ALL);


        // Join to bring together all the components for calculating TF-IDF
        // The D side of the join is smaller so it goes on the RHS
        Pipe idfPipe = new HashJoin(dfPipe, lhs_join, dPipe, rhs_join);

        // The IDF side of the join is smaller so it goes on the RHS
        Pipe tfidfPipe = new CoGroup(tfPipe, tf_token, idfPipe, df_token);

        // Calculate the TF-IDF weights per token per document
        Fields tfidf = new Fields("tfidf");
        String expression = "(double) tf_count * Math.log((double) n_docs / (1.0 + df_count))";
        ExpressionFunction tfidfExpression = new ExpressionFunction(tfidf, expression, Double.class);
        Fields tfidfArguments = new Fields("tf_count", "df_count", "n_docs");
        tfidfPipe = new Each(tfidfPipe, tfidfArguments, tfidfExpression, Fields.ALL);

        fieldSelector = new Fields("tf_token", "doc_id", "tfidf");
        tfidfPipe = new Retain(tfidfPipe, fieldSelector);
        tfidfPipe = new Rename(tfidfPipe, tf_token, token);

        // Keep track of the word counts which are useful for QA
        Pipe wcPipe = new Pipe("wc", tfPipe);
        Fields count = new Fields("count");
        wcPipe = new SumBy(wcPipe, tf_token, tf_count, count, Long.class);
        wcPipe = new Rename(wcPipe, tf_token, token);

        // Additionally sort by count
        wcPipe = new GroupBy(wcPipe, count, count);

        // Connect the taps & pipes
        FlowDef flowDef = FlowDef.flowDef()
                .setName("tfidf")
                .addSource(docPipe, docTap)
                .addSource(stopPipe, stopTap)
                .addTailSink(tfidfPipe, tfidfTap)
                .addTailSink(wcPipe, wcTap);

        // Write the DOT debug file
        Flow wcFlow = flowConnector.connect(flowDef);
        wcFlow.writeDOT(dotPath);
        wcFlow.complete();
    }
}
