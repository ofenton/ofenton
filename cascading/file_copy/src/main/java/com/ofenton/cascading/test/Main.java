package com.ofenton.cascading.test;

import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop2.Hadoop2MR1FlowConnector;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;

import java.util.Properties;

/**
 * Created by ofenton on 03/06/2015.
 */
public class Main {
        public static void main( String[] args) {
            String inPath = args[0];
            String outPath = args[1];

            Properties properties = new Properties();
            AppProps.setApplicationJarClass(properties, Main.class);
            FlowConnector flowConnector = new Hadoop2MR1FlowConnector(properties);

            // create the source tap
            Tap inTap = new Hfs(new TextDelimited(true, "\t"), inPath);

            // create the sink tap
            Tap outTap = new Hfs(new TextDelimited(true, "\t"), outPath);

            // specify a pipe to connect the taps
            Pipe copyPipe = new Pipe("copy");

            // connect the taps, pipes, etc., into a flow
            FlowDef flowDef = FlowDef.flowDef()
                    .addSource(copyPipe, inTap)
                    .addTailSink(copyPipe, outTap);

            // run the flow
            flowConnector.connect(flowDef).complete();
        }
}
