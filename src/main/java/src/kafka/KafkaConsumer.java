package src.kafka;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.serializer.StringDecoder;
//import kafka.utils.VerifiableProperties;

public class KafkaConsumer {
//	private final ConsumerConnector consumer;
//
//    public KafkaConsumer() {
//        Properties props = new Properties();
//        //zookeeper 配置
//        props.put("zookeeper.connect", "192.168.0.109:12181,192.168.0.112:12181,192.168.0.113:12181");
//        //group 代表一个消费组
//        props.put("group.id", "jd-group");
//
//        //zk连接超时
//        props.put("zookeeper.session.timeout.ms", "4000");
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("auto.offset.reset", "smallest");
//        //序列化类
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        ConsumerConfig config = new ConsumerConfig(props);
//        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
//    }
//
//    public void consume() {
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put("orderinfo", new Integer(1));
//
//        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
//        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
//
//        Map<String, List<KafkaStream<String, String>>> consumerMap = 
//                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
//        KafkaStream<String, String> stream = consumerMap.get("orderinfo").get(0);
//        ConsumerIterator<String, String> it = stream.iterator();
//        while (it.hasNext())
//            System.out.println(it.next().message());
//    }
//    
//    public void stop(){
//        try {
//            consumer.shutdown();
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        
//    }
//    public static void main(String[] args) {
//        new KafkaConsumer().consume();
//    }
}
