package oop.v3;


import oop.v2.RequestStat;

import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-04-05 15:47
 */

public interface StatViewer {
    void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills);
}