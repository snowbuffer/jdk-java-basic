package oop.v2;

/**
 * Description:  常规重构(面向对象)
 *
 * @author cjb
 * @since 2020-04-05 15:47
 */

/**
 * 如何进行面向对象设计？
 * 1. 划分职责进而识别出有哪些类
 * 2. 定义类及其属性和方法
 * 3. 定义类与类之间的交互关系
 * 4. 将类组装起来并提供执行入口
 * 5. Review 设计与实现: SOLID、KISS、DRY、YAGNI、LOD
 * <p>
 * MetricsCollector：负责打点采集原始数据，包括记录每次接口请求的响应时间和请求时间戳，并调用 MetricsStorage 提供的接口来存储这些原始数据。
 * MetricsStorage 和 RedisMetricsStorage：负责原始数据的存储和读取。
 * Aggregator：是一个工具类，负责各种统计数据的计算，比如响应时间的最大值、最小值、平均值、百分位值、接口访问次数、tps。
 * ConsoleReporter 和 EmailReporter：相当于一个上帝类（God Class），定时根据给定的时间区间，从数据库中取出数据，借助 Aggregator 类完成统计工作，并将统计结果输出到相应的终端，比如命令行、邮件。
 */
public class Demo {
    public static void main(String[] args) {
        MetricsStorage storage = new RedisMetricsStorage();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage);
        consoleReporter.startRepeatedReport(60, 60);

        EmailReporter emailReporter = new EmailReporter(storage);
        emailReporter.addToAddress("abc@qq.com");
        emailReporter.startDailyReport();

        MetricsCollector collector = new MetricsCollector(storage);
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