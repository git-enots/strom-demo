package logAnalysis;

import java.util.UUID;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;

public class OrdersAnalysisTopology {
	private static String topicName = "orderinfo";
	private static String zkRoot = "/stormKafka/"+topicName;
	
	public static void main(String[] args) {
		
		BrokerHosts hosts = new ZkHosts("ymhHadoop:2181,ymhHadoop2:2181,ymhHadoop3:2181");
 
		
		SpoutConfig spoutConfig = new SpoutConfig(hosts,topicName,zkRoot,UUID.randomUUID().toString());
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
		
		TopologyBuilder builder = new TopologyBuilder();        
		builder.setSpout("kafkaSpout",kafkaSpout);        
		builder.setBolt("merchantsSalesBolt", new MerchantsSalesAnalysisBolt(), 2).shuffleGrouping("kafkaSpout");
 
		Config conf = new Config();
		conf.setDebug(true);
		
		if(args != null && args.length > 0) {
			conf.setNumWorkers(1);
			try {
				
					StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
			} 
			catch (AuthorizationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			} catch (AlreadyAliveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {			
			conf.setMaxSpoutPending(3);		
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("ordersAnalysis", conf, builder.createTopology());
		}
 
	}

}
