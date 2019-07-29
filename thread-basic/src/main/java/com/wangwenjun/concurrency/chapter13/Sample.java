package com.wangwenjun.concurrency.chapter13;

import java.io.Serializable;

public class Sample implements Serializable, AAAA {

    public int a = 1;

    public Sample(){
        System.out.println("Smaple is loaded by " + this.getClass().getClassLoader());
        new Dog();
    }
}