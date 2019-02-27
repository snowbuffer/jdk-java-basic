package com.wangwenjun.java8;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/13 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CompletableFutureInAction3 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });

       /* CompletableFuture.supplyAsync(CompletableFutureInAction1::get, executor)
                .thenApply(CompletableFutureInAction3::multiply)
                .whenComplete((v, t) -> Optional.ofNullable(v).ifPresent(System.out::println));*/

        List<Integer> productionIDs = Arrays.asList(1, 2, 3, 4, 5);
        productionIDs.stream()
                .map((i) -> {
                    System.out.println("++" + i);
                    return i;
                }) // 多个map质检调用流程是同步的，同属于同一个线程，类似于模板方法
                .map((i) -> {
                    System.out.println("==" + i);
                    return i;
                })
                .forEach(System.out::println);

        /**
         *
         * productionIDs.stream()
         *                 .map((i) -> {System.out.println("++" + i); return i;}) // 类似于模板方法
         *                 .map((i) -> {System.out.println("==" + i); return i;})
         *                 .forEach(System.out::println);
         *  等效于：
         *
         *  for(int i = 0 ; i< productionIDs.size; i++) {
         *      int item = productionIDs.get(i);
         *      Object result1 = map((item) -> {System.out.println("++" + item); return item;})
         *      Object result2 = map((result1) -> {System.out.println("==" + result1); return result1;})
         *      System.out.println(result2);
         *  }
         */

        List<Double> result = productionIDs
                .stream()
                .map(i -> CompletableFuture.supplyAsync(() -> queryProduction(i)))
                .map(future -> future.thenApply(CompletableFutureInAction3::multiply))
                .map(CompletableFuture::join).collect(toList());

        System.out.println(result);
    }

    private static double multiply(double value) {
        try {
            System.out.println(Thread.currentThread().getName() + "multiply");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value * 10d;
    }

    private static double queryProduction(int i) {
        System.out.println(Thread.currentThread().getName() + "queryProduction");
        return CompletableFutureInAction1.get();
    }
}
