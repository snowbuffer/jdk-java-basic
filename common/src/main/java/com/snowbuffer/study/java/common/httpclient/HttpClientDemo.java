package com.snowbuffer.study.java.common.httpclient;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-10-20 00:34
 */
public class HttpClientDemo {

    public static void main(String[] args) throws Exception {

        CloseableHttpClient httpClient = HttpClients
                .custom()
                /**
                 * 时间轴 ->>>>>>>>>
                 *      可并发执行的队列:
                 *           ->>>>[]  setMaxConnPerRoute
                 *                          future排队队列
                 *                                  ->>>>[] pending队列
                 *
                 *          |<-      setMaxConnTotal    ->|  时间上可以超出setMaxConnTotal最大值，但不会报错，只会累计错误
                 *  Note: 可并发执行的队列 + future排队队列 >= setMaxConnTotal
                 *  总结：只有当可并发执行的队列执行完成，才轮到future排队队列
                 *
                 *  .setMaxConnPerRoute(10).setMaxConnTotal(10) 立即执行10个任务
                 *  .setMaxConnPerRoute(10).setMaxConnTotal(1) 一个一个任务串行执行
                 *  .setMaxConnPerRoute(10).setMaxConnTotal(15) 第一批执行：10个任务，第二批执行5个任务
                 *
                 */
                .setMaxConnPerRoute(1) // 并发数, 默认值:2 即：一次从可执行队列中取多少个连接
                .setMaxConnTotal(15) // 最大被允许的连接数，默认值：20 (所有route共享，可执行队列)， 超出部分会放入future队列
                /**
                 * 单 个connection连接的存活时间 注：过期的连接会在下次获取连接是自动移除，并重新创建新的连接(参考AbstractConnPool)
                 */
                .setConnectionTimeToLive(5, TimeUnit.MINUTES)
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                /**
                 * 三次握手建立连接时间
                 */
                .setConnectTimeout(10000)
                /**
                 * 从AbstractConnPool获取连接时间
                 */
                .setConnectionRequestTimeout(2000)
                /**
                 * 三次握手建立连接后，后续服务每次response给client的最大超时时间，服务端每次refresh，重新计时
                 */
                .setSocketTimeout(600000).build();

//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(new Runnable() {
//                @SneakyThrows
//                @Override
//                public void run() {
//                    HttpGet httpGet = new HttpGet();
//                    httpGet.setConfig(requestConfig);
//                    StringBuilder urlStr = new StringBuilder();
//                    urlStr.append("http://127.0.0.1:8080/");
//                    httpGet.setURI(URI.create(urlStr.toString()));
//                    CloseableHttpResponse execute = null;
//                    try {
//                        execute = httpClient.execute(httpGet);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    HttpEntity reposeEntity = execute.getEntity();
//                    if (reposeEntity != null) {
//                        System.out.println(EntityUtils.toString(reposeEntity, "UTF-8"));
//                    }
//                    EntityUtils.consume(reposeEntity);
//                }
//            });
//            thread.setName("i=" + i);
//            thread.start();
//        }
//
//        Thread.currentThread().join();

        for (int i = 0; i < 10; i++) {
            HttpGet httpGet = new HttpGet();
            httpGet.setConfig(requestConfig);
            StringBuilder urlStr = new StringBuilder();
            urlStr.append("http://127.0.0.1:8080/");
            httpGet.setURI(URI.create(urlStr.toString()));
            CloseableHttpResponse execute = null;
            try {
                execute = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity reposeEntity = execute.getEntity();
            if (reposeEntity != null) {
                System.out.println(EntityUtils.toString(reposeEntity, "UTF-8")); // 只是toString, 然后连接还是放回了连接池
            }
            EntityUtils.consume(reposeEntity); // 只是关闭了inputstream, 然后连接还是放回了连接池

            execute.close(); // 如果连接已经放回了连接池，那么这个方法无法真实关闭连接

        }


    }
}
