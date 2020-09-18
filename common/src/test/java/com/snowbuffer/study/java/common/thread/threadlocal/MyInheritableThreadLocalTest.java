package com.snowbuffer.study.java.common.thread.threadlocal;

import com.snowbuffer.study.java.common.thread.threadlocal.domain.Person;
import com.snowbuffer.study.java.common.thread.threadlocal.MyInheritableThreadLocal;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 22:27
 */
public class MyInheritableThreadLocalTest {

    /**
     * 测试 多个ThreadLocal 绑定到同一个线程中
     */
    @Test
    public void test1() throws InterruptedException {
        Person person = new Person();
        person.setAge(12);
        MyInheritableThreadLocal.threadLocal1.set(person);

        MyInheritableThreadLocal.threadLocal2.set(person);

        System.out.println("main: -> threadLocal1" + MyInheritableThreadLocal.threadLocal1.get());
        System.out.println("main: -> threadLocal2" + MyInheritableThreadLocal.threadLocal2.get());

        MyThread myThread = new MyThread(); // 子线程可以获取父线程的数据
        myThread.start();

        TimeUnit.SECONDS.sleep(5);
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread -> threadLocal1: " + MyInheritableThreadLocal.threadLocal1.get());
            System.out.println("MyThread -> threadLocal2:" + MyInheritableThreadLocal.threadLocal2.get());
        }
    }


    /**
     * 测试： threadLocals copy to inheritableThreadLocals (对象共享)
     */
    @Test
    public void test2() throws InterruptedException {
        Person person = new Person();
        person.setAge(12);
        MyInheritableThreadLocal.threadLocal1.set(person);
        System.out.println("main: before-> threadLocal1" + MyInheritableThreadLocal.threadLocal1.get());

        MyThread2 myThread2 = new MyThread2(); // 子线程可以获取父线程的数据
        myThread2.start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("main: after -> threadLocal1" + MyInheritableThreadLocal.threadLocal1.get());


    }

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread2: before-> threadLocal1" + MyInheritableThreadLocal.threadLocal1.get()); // 继承
            Person person = new Person();
            person.setAge(15);
            MyInheritableThreadLocal.threadLocal1.set(person); // 只更改自己的localMap
            System.out.println("MyThread2: after-> threadLocal1" + MyInheritableThreadLocal.threadLocal1.get());
        }
    }

    /**
     * 测试： threadLocals copy to inheritableThreadLocals (对象隔离)
     */
    @Test
    public void test3() throws InterruptedException {
        Person person = new Person();
        person.setAge(12);
        MyInheritableThreadLocal.threadLocal3.set(person);
        System.out.println("main: before-> threadLocal3" + MyInheritableThreadLocal.threadLocal3.get());

        MyThread3 myThread3 = new MyThread3(); // 子线程可以获取父线程的数据
        myThread3.start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("main: after -> threadLocal3" + MyInheritableThreadLocal.threadLocal3.get());


    }

    private static class MyThread3 extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread2: before-> threadLocal1" + MyInheritableThreadLocal.threadLocal3.get()); // 继承
            Person person = MyInheritableThreadLocal.threadLocal3.get();
            person.setAge(100);
            System.out.println("MyThread2: after-> threadLocal1" + MyInheritableThreadLocal.threadLocal3.get());
        }
    }
}