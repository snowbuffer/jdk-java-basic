package oop.v4;

import com.snowbuffer.spring.guide.test.v2.RequestInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:  主要是添加默认的构造器，减少外部设置的成本
 *
 * @author cjb
 * @since 2020-04-05 15:47
 */

public class Demo {
    public static void main(String[] args) {
        ConsoleReporter consoleReporter = new ConsoleReporter();
        consoleReporter.startRepeatedReport(60, 60);
        List emailToAddresses = new ArrayList<>();
        emailToAddresses.add("abc@qq.com");
        EmailReporter emailReporter = new EmailReporter(emailToAddresses);
        emailReporter.startDailyReport();
        MetricsCollector collector = new MetricsCollector();
        collector.recordRequest(new RequestInfo("register", 123, 10234));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12334));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}