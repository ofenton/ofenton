package ofenton.kafka_test.spark;

import com.google.common.collect.Lists;
import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Created by ofenton on 11/20/15.
 */
public class WordCountConsumer extends Thread implements Serializable {

  private static final Pattern SPACE = Pattern.compile(" ");
  private final JavaStreamingContext jsc;
  private final JavaPairInputDStream<String, String> directKafkaStream;

  WordCountConsumer(String master, String broker, String topic) {
    System.out.println("starting up Spark-Kafka WordCount consumer");
    // Create context with a 2 seconds batch interval
    SparkConf sparkConf = new SparkConf().setAppName("JavaDirectKafkaWordCount");
    sparkConf.setMaster(master);
    this.jsc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

    HashSet<String> topicSet = new HashSet<>(Arrays.asList(new String[] { topic }));
    HashMap<String, String> kafkaParams = new HashMap<>();
    kafkaParams.put("metadata.broker.list", broker);

    System.out.println("configured Spark-Kafka context");

    // Create direct kafka stream with brokers and topics
    directKafkaStream = KafkaUtils.createDirectStream(jsc, String.class, String.class, StringDecoder.class, StringDecoder.class,
    kafkaParams, topicSet);

    System.out.println("created Kafka-Spark input bridge");
  }

  @Override public void run() {

    System.out.println("configuring & starting context");

    // Get the lines, split them into words, count the words and print
    JavaDStream<String> lines = directKafkaStream.map(
        (Tuple2<String, String> tuple2) -> { return tuple2._2(); }
    );

    JavaDStream<String> words = lines.flatMap(
        (String x) -> { return Lists.newArrayList(SPACE.split(x)); }
    );

    JavaPairDStream<String, Integer> wordCounts =
        words.mapToPair(w -> new Tuple2<String, Integer>(w, 1))
            .reduceByKey((x, y) -> x + y);

    wordCounts.print();
    //wordCounts.saveAsTextFile("hdfs://counts.txt");

    jsc.start();
    System.out.println("running context");
    jsc.awaitTermination();
    System.out.println("terminating context");
  }
}
