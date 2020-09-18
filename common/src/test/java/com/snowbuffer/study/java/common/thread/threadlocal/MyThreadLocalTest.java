package com.snowbuffer.study.java.common.thread.threadlocal;

import com.snowbuffer.study.java.common.thread.threadlocal.domain.Person;
import com.snowbuffer.study.java.common.thread.threadlocal.MyThreadLocal;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 19:39
 */
public class MyThreadLocalTest {

    @Test
    public void test1() {
        new ThreadThree().start();
    }

    @Test
    public void test2() throws InterruptedException {
        new ThreadOne().start();
        TimeUnit.MILLISECONDS.sleep(100);
        new ThreadTwo().start();
        TimeUnit.SECONDS.sleep(5);
    }

    private static class ThreadThree extends Thread {
        @Override
        public void run() {

            Person person1 = new Person();
            person1.setAge(1);
            Person person2 = new Person();
            person2.setAge(2);
            Person person3 = new Person();
            person3.setAge(3);

            MyThreadLocal.threadLocal1.set(person1);
            MyThreadLocal.threadLocal2.set(person2);
            MyThreadLocal.threadLocal3.set(person3);

            System.out.println(MyThreadLocal.threadLocal1.get());
            System.out.println(MyThreadLocal.threadLocal2.get());
            System.out.println(MyThreadLocal.threadLocal3.get());


        }
    }


    /**
     * 线程1
     *
     * @author joonwhee
     * @date 2018年2月24日
     */
    private static class ThreadOne extends Thread {
        @Override
        public void run() {
            Person person = new Person();
            MyThreadLocal.threadLocal.set(person);
            person.setAge(13);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadOne: " + MyThreadLocal.threadLocal.get());
        }
    }

    /**
     * 线程2
     *
     * @author joonwhee
     * @date 2018年2月24日
     */
    private static class ThreadTwo extends Thread {
        @Override
        public void run() {
            Person person = new Person();
            MyThreadLocal.threadLocal.set(person);
            person.setAge(15);
            System.out.println("ThreadTwo: " + MyThreadLocal.threadLocal.get());
        }
    }
}