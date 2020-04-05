package oop.v3;


import oop.v2.RequestInfo;
import oop.v2.RequestStat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-04-05 15:47
 */

public class Aggregator {

    public Map<String, RequestStat> aggregate(Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
        Map requestStats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List requestInfosPerApi = entry.getValue();
            RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    private RequestStat doAggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        List respTimes = new ArrayList<>();
        for (RequestInfo requestInfo : requestInfos) {
            double respTime = requestInfo.getResponseTime();
            respTimes.add(respTime);
        }
        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(max(respTimes));
        requestStat.setMinResponseTime(min(respTimes));
        requestStat.setAvgResponseTime(avg(respTimes));
        requestStat.setP999ResponseTime(percentile999(respTimes));
        requestStat.setP99ResponseTime(percentile99(respTimes));
        requestStat.setCount(respTimes.size());
        requestStat.setTps((long) tps(respTimes.size(), durationInMillis / 1000));
        return requestStat;
    }

    private double max(List dataset) {
        return 0d;
    }

    private double min(List dataset) {
        return 0d;
    }

    private double avg(List dataset) {
        return 0d;
    }

    private double tps(int count, double duration) {
        return 0d;
    }

    private double percentile999(List dataset) {
        return 0d;
    }

    private double percentile99(List dataset) {
        return 0d;
    }

    private double percentile(List dataset, double ratio) {
        return 0d;
    }
}