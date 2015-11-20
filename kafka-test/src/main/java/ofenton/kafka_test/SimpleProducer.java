package ofenton.kafka_test;

import kafka.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import scala.collection.JavaConversions;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ofenton on 11/18/15.
 */
public class SimpleProducer implements AutoCloseable {

  private final Producer producer;

  public SimpleProducer(Properties kafkaConfig) {
    ProducerConfig config = new ProducerConfig(kafkaConfig);
    producer = new Producer(config);
  }

  public void send(KeyedMessage<Integer, String> message) {
    List<KeyedMessage> messages = new ArrayList<>();
    messages.add(message);

    producer.send(JavaConversions.asScalaBuffer(messages));
  }

  public void close() {
    producer.close();
  }
}
