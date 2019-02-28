package com.wangwenjun.java8;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FutureInAction_My {

    public static void main(String[] args) {

        MyFuture<Integer> myFuture = invoke(() -> {
            Random random = new Random(System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return random.nextInt(5);
        }, new Completable<Integer>() {
            @Override
            public void complete(Integer result) {
                System.out.println("result => " + result);
            }

            @Override
            public void exception(Throwable throwable) {
                System.out.println("exception => " + throwable.getLocalizedMessage());
            }
        });

        System.out.println(myFuture.get());
        System.out.println(".........");
        System.out.println(myFuture.get());
        System.out.println(myFuture.get());
    }


    public static <Result> MyFuture<Result> invoke(Callable<Result> callable, Completable<Result> completable) {
        AtomicReference<Result> result = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean(false);

        MyFuture<Result> myFuture = new MyFuture<Result>() {
            private Completable<Result> completable;

            @Override
            public Result get() {
                return result.get();
            }

            @Override
            public boolean isFinished() {
                return finished.get();
            }

            @Override
            public void setCompletable(Completable<Result> completable) {
                this.completable = completable;
            }

            @Override
            public Completable<Result> getCompletable() {
                return this.completable;
            }
        };
        myFuture.setCompletable(completable);

        new Thread(() -> {
            try {
                Result call = callable.call();
                result.set(call);
                finished.set(true);
                myFuture.getCompletable().complete(call);
            } catch (Exception e) {
                myFuture.getCompletable().exception(e);
            }

        }).start();
        return myFuture;
    }

    private interface Callable<Result> {
        Result call();
    }

    private interface MyFuture<Result> {

        Result get();

        boolean isFinished();

        void setCompletable(Completable<Result> completable);

        Completable<Result> getCompletable();

    }

    private interface Completable<Result> {

        void complete(Result result);

        void exception(Throwable throwable);
    }
}
