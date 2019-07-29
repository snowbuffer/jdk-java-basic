package com.wangwenjun.concurrency.chapter13;

public class Dog {

    public int b = 1;

    public Dog(){
        System.out.println("Dog is loaded by " + this.getClass().getClassLoader());
    }
}