package trident;
import java.util.Map;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
 
@SuppressWarnings("rawtypes")
public class OutbreakTrendFactory implements StateFactory {
    private static final long serialVersionUID = 1L;
 
    @Override
    public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
        return new OutbreakTrendState(new OutbreakTrendBackingMap());

}
}
