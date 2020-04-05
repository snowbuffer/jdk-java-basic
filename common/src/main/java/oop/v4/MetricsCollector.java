package oop.v4;

import com.snowbuffer.spring.guide.test.v2.MetricsStorage;
import com.snowbuffer.spring.guide.test.v2.RedisMetricsStorage;
import com.snowbuffer.spring.guide.test.v2.RequestInfo;
import org.springframework.util.StringUtils;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-04-05 15:47
 */

public class MetricsCollector {
    private MetricsStorage metricsStorage;

    // 兼顾代码的易用性，新增一个封装了默认依赖的构造函数
    public MetricsCollector() {
        this(new RedisMetricsStorage());
    }

    // 兼顾灵活性和代码的可测试性，这个构造函数继续保留
    public MetricsCollector(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
    }

    //用一个函数代替了最小原型中的两个函数
    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo == null || StringUtils.isEmpty(requestInfo.getApiName())) {
            return;
        }
        metricsStorage.saveRequestInfo(requestInfo);
    }
}
