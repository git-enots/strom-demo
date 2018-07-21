package logAnalysis;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MerchantsSalesAnalysisBolt extends BaseRichBolt{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7802497274919732924L;
	@SuppressWarnings("unused")
	private OutputCollector _collector;
	LogInfoHandler loginfohandler;
	JedisPool pool;
	public void execute(Tuple tuple) {
		String orderInfo = tuple.getString(0);
		OrdersBean order = loginfohandler.getOrdersBean(orderInfo);
		
		System.out.println(order.toString()+"=======");
		//store the salesByMerchant infomation into Redis
		Jedis jedis = pool.getResource();
		jedis.zincrby("orderAna:topSalesByMerchant", order.getTotalPrice(), order.getMerchantName());
	}

	public void prepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector collector) {
		this._collector = collector;
		this.loginfohandler = new LogInfoHandler();
		this.pool = new JedisPool(new JedisPoolConfig(), "ymhHadoop",6379,2 * 60000);
		
	}
 
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}
 

}
