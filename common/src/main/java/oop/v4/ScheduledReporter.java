package oop.v4;

import oop.v2.MetricsStorage;
import oop.v2.RequestInfo;
import oop.v2.RequestStat;
import oop.v3.Aggregator;
import oop.v3.StatViewer;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-04-05 15:47
 */

public abstract class ScheduledReporter {
    protected MetricsStorage metricsStorage;
    protected Aggregator aggregator;
    protected StatViewer viewer;

    public ScheduledReporter(MetricsStorage metricsStorage, Aggregator aggregator, StatViewer viewer) {
        this.metricsStorage = metricsStorage;
        this.aggregator = aggregator;
        this.viewer = viewer;
    }

    protected void doStatAndReport(long startTimeInMillis, long endTimeInMillis) {
        long durationInMillis = endTimeInMillis - startTimeInMillis;
        Map<String, List<RequestInfo>> requestInfos =
                metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
        Map<String, RequestStat> requestStats = aggregator.aggregate(requestInfos, durationInMillis);
        viewer.output(requestStats, startTimeInMillis, endTimeInMillis);
    }

}