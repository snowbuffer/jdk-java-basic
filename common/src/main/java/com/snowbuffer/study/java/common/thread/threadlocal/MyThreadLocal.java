package com.snowbuffer.study.java.common.thread.threadlocal;


import com.snowbuffer.study.java.common.thread.threadlocal.domain.Person;

public class MyThreadLocal {

    public static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static ThreadLocal<Person> threadLocal1 = new ThreadLocal<>();

    public static ThreadLocal<Person> threadLocal2 = new ThreadLocal<>();

    public static ThreadLocal<Person> threadLocal3 = new ThreadLocal<>();

    /**
     * 参考地址：https://blog.csdn.net/v123411739/article/details/79117430
     *
     * Thread.threadLocals == ThreadLocalMap  {                                    <- |
     *     table: [{                                                                  |
     *         entry: threadLocalObject1(threadLocal1) -> value -> personObject1      |  <- |
     *     },{                                                                        |     |
     *         entry: threadLocalObject2(threadLocal2) -> value -> personObject2      |     |
     *     },{                                                                        |     |
     *         entry: threadLocalObject3(threadLocal3) -> value -> personObject3      |     |
     *     },{                                                                        |     |
     *         entry: threadLocalObject3(threadLocal) -> value -> personObject        |     |
     *     }]                                                                         |     |
     * }                                                                              |     |
     *                                                                                |     |
     * threadLocal1.get()                                                             |     |
     *      -> getMap(Thread.currentThead)                                            |     |
     *          -> Thread.currentThead.threadLocals                                -> |     |
     *                  -> ThreadLocalMap计算tableIndex， 并找到对应的 entry                -> |
     *                          -> personObject1
     *
     * 一个线程可以容纳多个ThreadLocal对象，每个ThreadLocal对象值对应的是业务存储的内容
     *
     *
     */


}