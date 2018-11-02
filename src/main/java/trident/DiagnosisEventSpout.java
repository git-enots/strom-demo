package trident;
import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Fields;
import org.apache.storm.trident.spout.ITridentSpout;
@SuppressWarnings("rawtypes")
public class DiagnosisEventSpout implements ITridentSpout<Long> {
    private static final long serialVersionUID = 1L;
    BatchCoordinator<Long> coordinator = new DefaultCoordinator();
    Emitter<Long> emitter = new DiagnosisEventEmitter();
 
    @Override
    public BatchCoordinator<Long> getCoordinator(String txStateId, Map conf, TopologyContext context) {
        return coordinator;
    }
 
    @Override
    public Emitter<Long> getEmitter(String txStateId, Map conf, TopologyContext context) {
        return emitter;
    }
 
    @Override
    public Map getComponentConfiguration() {
        return null;
    }
 
    @Override
    public Fields getOutputFields() {
        return new Fields("event");
    }
}