package com.wangwenjun.concurrent.chapter1;

/***************************************
 * @author:Alex Wang
 * @Date:2017/3/12 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class SingletonObject4 {

    private static SingletonObject4 instance;

    private SingletonObject4() {
        //---
    }

    //double check
    public static SingletonObject4 getInstance() {

        if (null == instance) {
            synchronized (SingletonObject4.class) {
                if (null == instance)
                    instance = new SingletonObject4(); // 实例化一个对象，需要很多过程，可能会导致该实例内部的成员变量为null
            }
        }

        return SingletonObject4.instance;
    }
}
