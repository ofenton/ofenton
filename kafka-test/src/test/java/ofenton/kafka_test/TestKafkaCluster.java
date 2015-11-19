package ofenton.kafka_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import org.I0Itec.zkclient.ZkClient;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestKafkaCluster {
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
    }

    @AfterTest
    public void tearDown() {
        kafkaServer.shutdown();
        zkClient.close();
        zkServer.shutdown();
    }

    @Test
    public void testProducer() {
        // setup producer
        Properties properties = TestUtils.getProducerConfig("localhost:" + port, "kafka.producer.DefaultPartitioner");

        try (SimpleProducer producer = new SimpleProducer(properties)) {
            KeyedMessage<Integer, String> message = new KeyedMessage(topic, "test-message");
            producer.send(message);
        }
    }
}
