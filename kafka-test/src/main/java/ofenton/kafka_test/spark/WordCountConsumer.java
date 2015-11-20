package ofenton.kafka_test.spark;

import com.google.common.collect.Lists;
import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
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

  WordCountConsumer(String master, String broker, String topic) {
    System.out.println("starting up Spark-Kafka WordCount consumer");
    // Create context with a 2 seconds batch interval
    SparkConf sparkConf = new SparkConf().setAppName("JavaDirectKafkaWordCount");
    sparkConf.setMaster(master);
    this.jsc = new JavaStreamingContext(sparkConf, Durations.seconds(2));

    HashSet<String> topicSet = new HashSet<>(Arrays.asList(new String[] { topic }));
    HashMap<String, String> kafkaParams = new HashMap<>();
    kafkaParams.put("metadata.broker.list", broker);

    System.out.println("configured Spark-Kafka context");

    // Create direct kafka stream with brokers and topics
    JavaPairInputDStream<String, String> directKafkaStream =
        KafkaUtils.createDirectStream(jsc, String.class, String.class, StringDecoder.class, StringDecoder.class,
    kafkaParams, topicSet);

    System.out.println("created Kafka-Spark input bridge");

    // Get the lines, split them into words, count the words and print
    JavaDStream<String> lines = directKafkaStream.map(new Function<Tuple2<String, String>, String>() {
      @Override
      public String call(Tuple2<String, String> tuple2) {

        System.out.println("DATA:" + tuple2.toString());
        return tuple2._2();
      }
    });

    JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
      @Override
      public Iterable<String> call(String x) {

        System.out.println("FLATTEN:" + x);
        return Lists.newArrayList(SPACE.split(x));
      }
    });

    JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
        new PairFunction<String, String, Integer>() {
          @Override
          public Tuple2<String, Integer> call(String s) {

            System.out.println("PAIR:" + s);
            return new Tuple2<String, Integer>(s, 1);
          }
        }).reduceByKey(
        new Function2<Integer, Integer, Integer>() {
          @Override
          public Integer call(Integer i1, Integer i2) {

            System.out.println("REDUCE:" + i1 + ":" + i2);
            return i1 + i2;
          }
        });

    wordCounts.print();
  }

  @Override public void run() {
    System.out.println("starting context");
    jsc.start();
    System.out.println("running context");
    jsc.awaitTermination();
    System.out.println("terminating context");
  }
}
