package trident;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.tuple.Fields;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;

public class OutbreakDetectionTopology {
	public static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();
        DiagnosisEventSpout spout = new DiagnosisEventSpout();
        //创建事件流:流中包含经纬度\时间戳\疾病的ICD9-CM码
        Stream inputStream = topology.newStream("event", spout);
        //DiseaseFilter()对疾病事件过滤
        inputStream.each(new Fields("event"), new DiseaseFilter())
                //事件打城市标识,这样流中多出一个"city"
                .each(new Fields("event"), new CityAssignment(), new Fields("city"))
                //HourAssignment ()增加一个"hour","cityDiseaseHour" 到流中,cityDiseaseHour为包含城市\疾病码\时间的tuple用于分组
                .each(new Fields("event", "city"), new HourAssignment(), new Fields("hour", "cityDiseaseHour"))
                //已cityDiseaseHour分组(对一个时段一个城市中的疾病为单位分组)
                .groupBy(new Fields("cityDiseaseHour"))
                //持久化聚合存储
                .persistentAggregate(new OutbreakTrendFactory(), new Count(), new Fields("count")).newValuesStream()
                //OutbreakDetector判断("cityDiseaseHour", "count")是否有疾病阈值,产生报警
                .each(new Fields("cityDiseaseHour", "count"), new OutbreakDetector(), new Fields("alert"))
                //DispatchAlert接收报警,记录退出流
                .each(new Fields("alert"), new DispatchAlert(), new Fields());
        return topology.build();
    }
 
    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("cdc", conf, buildTopology());
        Thread.sleep(20000);
        cluster.shutdown();
    }
}
