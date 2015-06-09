package com.ofenton.cascading.hashjoin;


import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop2.Hadoop2MR1FlowConnector;
import cascading.operation.aggregator.Count;
import cascading.operation.regex.RegexFilter;
import cascading.operation.regex.RegexSplitGenerator;
import cascading.pipe.*;
import cascading.pipe.assembly.Retain;
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
        String wcPath = args[1];
        String stopPath = args[2];

        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, Main.class);
        FlowConnector flowConnector = new Hadoop2MR1FlowConnector(properties);

        // Create source & sink taps
        Tap docTap = new Hfs(new TextDelimited(true, "\t"), docPath);
        Tap wcTap = new Hfs(new TextDelimited(true, "\t"), wcPath + "/rain");

        Fields stop = new Fields("stop");
        Tap stopTap = new Hfs(new TextDelimited(stop, true, "\t"), stopPath);

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

        // Determine the word counts
        Pipe wcPipe = new Pipe("wc", tokenPipe);
        wcPipe = new Retain(wcPipe, token);
        wcPipe = new GroupBy(wcPipe, token);
        wcPipe = new Every(wcPipe, Fields.ALL, new Count(), Fields.ALL);

        // Connect the taps & pipes
        FlowDef flowDef = FlowDef.flowDef()
                .setName("wc")
                .addSource(docPipe, docTap)
                .addSource(stopPipe, stopTap)
                .addTailSink(wcPipe, wcTap);

        // Write the DOT debug file
        Flow wcFlow = flowConnector.connect(flowDef);
        wcFlow.writeDOT(wcPath + "/dot/wc.dot");
        wcFlow.complete();
    }
}
