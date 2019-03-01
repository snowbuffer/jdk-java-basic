package com.wangwenjun.concurrent.chapter1;

import java.util.stream.IntStream;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 10:32
 */
public class SingletonObject7_my {

    private SingletonObject7_my() {
    }

    private enum Singleton {
        INSTANCE;

        private final SingletonObject7_my singletonObject7_my;

        Singleton() {
            System.out.println("==Singleton");
            singletonObject7_my = new SingletonObject7_my();
        }

        private SingletonObject7_my getInstance() {
            return singletonObject7_my;
        }
    }

    public static SingletonObject7_my getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).boxed().forEach((item) -> {
            System.out.println(SingletonObject7_my.getInstance());
        });
    }
}

