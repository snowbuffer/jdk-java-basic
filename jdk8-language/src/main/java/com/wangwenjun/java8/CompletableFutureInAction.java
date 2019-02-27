package com.wangwenjun.java8;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/8 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CompletableFutureInAction {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(10000L);
                future.complete(1000d);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }

        }).start();

        System.out.println("..............");
        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });*/

        // 等效上述代码，无需自定义thread过程
        /*CompletableFuture<Double> future = CompletableFuture.supplyAsync(CompletableFutureInAction::get);
        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });
//        future.join();  // 阻塞进行，直到任务完成
        System.out.println("..............");*/

        /*long start = System.currentTimeMillis();
        List<Double> doubles = Arrays.asList(
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble());
        System.out.println(doubles);
        List<CompletableFuture<Double>> futures = doubles
                .stream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return d * 10d;
                }))
                .collect(toList());

        List<Double> collect = futures.stream().map(f -> f.join()).collect(toList());
        System.out.println(collect);
        System.out.println(start-System.currentTimeMillis());*/

        /*Executor executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true); // 设置后台线程
            return t;
        });
        executor.execute(() -> System.out.println("sfsdfsfs"));*/

        Double value = CompletableFuture.supplyAsync(CompletableFutureInAction::get)
                .whenComplete((v, t) -> System.out.println(">>>>" + v)) // 先执行
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> i + 10)) // 后执行
                .get();
        System.out.println("==" + value);
    }

    private static double get() {
        try {
            Thread.sleep(10000L);
            double v = random.nextDouble();
            System.out.println(v);
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
