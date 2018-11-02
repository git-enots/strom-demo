package trident;
import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;
 
@SuppressWarnings("rawtypes")
public class OutbreakTrendFactory implements StateFactory {
    private static final long serialVersionUID = 1L;
 
    @Override
    public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
        return new OutbreakTrendState(new OutbreakTrendBackingMap());

}
}
