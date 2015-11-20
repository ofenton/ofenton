package ofenton.kafka_test.spark;

import kafka.admin.AdminUtils;
import kafka.producer.KeyedMessage;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.MockTime;
import kafka.utils.TestUtils;
import kafka.utils.TestZKUtils;
import kafka.utils.Time;
import kafka.utils.ZKStringSerializer$;
import kafka.zk.EmbeddedZookeeper;
import ofenton.kafka_test.SimpleProducer;
import org.I0Itec.zkclient.ZkClient;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ofenton on 11/20/15.
 */
public class TestWordCountConsumer {

  private int brokerId = 0;
  private int port;
  private String topic = "test";

  private KafkaServer kafkaServer;
  private ZkClient zkClient;
  private EmbeddedZookeeper zkServer;

  @BeforeTest
  public void setup() throws Exception {
    // setup Zookeeper
    String zkConnect = TestZKUtils.zookeeperConnect();
    zkServer = new EmbeddedZookeeper(zkConnect);
    zkClient = new ZkClient(zkServer.connectString(), 30000, 30000, ZKStringSerializer$.MODULE$);

    // setup Broker
    port = TestUtils.choosePort();
    Properties props = TestUtils.createBrokerConfig(brokerId, port);

    KafkaConfig config = new KafkaConfig(props);
    Time mock = new MockTime();
    kafkaServer = TestUtils.createServer(config, mock);

    // create topic
    AdminUtils.createTopic(zkClient, topic, 1, 1, new Properties());

    List<KafkaServer> servers = new ArrayList<>();
    servers.add(kafkaServer);
    TestUtils.waitUntilMetadataIsPropagated(scala.collection.JavaConversions.asScalaBuffer(servers), topic, 0, 5000);

    // Spark

  }

  @AfterTest
  public void tearDown() {
    kafkaServer.shutdown();
    zkClient.close();
    zkServer.shutdown();
  }

  @Test
  public void testWordCount() throws InterruptedException {
    // Given
    String[] lines = {
        "once upon a time",
        "there was a lovely lovely horse"
                     };

    // setup producer
    Properties properties = TestUtils.getProducerConfig("localhost:" + port, "kafka.producer.DefaultPartitioner");

    // When


    // Create Spark consumer
    //String master = "spark://localhost:7077";
    final int processes = 2;
    final int threadsPerProcess = 2;
    final int memoryPerProcessMb = 2048;
    //String sparkMaster = "local-cluster[" + processes + "," + threadsPerProcess + "," + memoryPerProcessMb + "]";
    String sparkMaster = "local[4]";
    WordCountConsumer consumer = new WordCountConsumer(sparkMaster, "localhost:" + port, topic);
    consumer.start();


    for (int i = 0; i < 100; i++) {
      try (SimpleProducer producer = new SimpleProducer(properties)) {
        for (String line : lines) {
          KeyedMessage<Integer, String> message = new KeyedMessage(topic, line);
          producer.send(message);
        }
      }
      Thread.sleep(1000);
    }

    try {
      consumer.interrupt();
      consumer.join();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    // Then
    System.out.println("complete");
  }
}
