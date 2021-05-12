package com.wangwenjun.juc.collections.blocking;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/***************************************
 * @author:Alex Wang
 * @Date:2017/9/12
 * QQ交流群:601980517，463962286
 ***************************************/
public class SynchronousQueueExampleTest {

    /**
     * SynchronousQueue ： 两个线程之前的同步控制， 本身没有容量的概念，一个元素存放到队列必须与一个元素取值同步发生才有效(即两个线程之间的同步交换数据)
     * 生产者线程  put(数据1) -> 等待         -> put成功
     * 消费者线程                     take               -> take结果：数据1
     * 以上规则跟exchange很像
     * 使用场景：观察者模式： 一个生产者发布事件，一个消费者负责及时响应事件
     */

    @Test
    public void testAdd() throws InterruptedException {
        SynchronousQueue<String> queue = SynchronousQueueExample.create();

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                assertThat(queue.remove(), equalTo("SynchronousQueue"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TimeUnit.MILLISECONDS.sleep(5);
        // add 不适合在SynchronousQueue使用
        assertThat(queue.add("SynchronousQueue"), equalTo(true));
    }

    /**
     * Producer Consumer
     * <p>
     * P------>queue<-----Consumer
     *
     * @throws InterruptedException
     */
    @Test
    public void testOffer() throws InterruptedException {
        SynchronousQueue<String> queue = SynchronousQueueExample.create();

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                assertThat(queue.take(), equalTo("SynchronousQueue"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TimeUnit.MILLISECONDS.sleep(5);
        assertThat(queue.offer("SynchronousQueue"), equalTo(true));

    }


    /**
     * Producer Consumer
     * <p>
     * P------>queue<-----Consumer
     *
     * @throws InterruptedException
     */
    @Test
    public void testPut() throws InterruptedException {
        SynchronousQueue<String> queue = SynchronousQueueExample.create();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                assertThat(queue.take(), equalTo("SynchronousQueue"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        queue.put("SynchronousQueue"); // 添加元素，一直等到有消费者取值才返回
        System.out.println("==");
    }

}