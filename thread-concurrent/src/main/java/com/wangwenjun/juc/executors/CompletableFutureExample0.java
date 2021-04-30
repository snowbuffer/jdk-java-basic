package com.wangwenjun.juc.executors;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-04-29 17:03
 */
public class CompletableFutureExample0 {

    // 一个CompletableFuture就是一个future, 本质上为future提供了自动回调功能

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @SneakyThrows
            @Override
            public Integer get() {
                TimeUnit.SECONDS.sleep(10); // 这段添加与不添加，是两个概念
                // 添加sleep定时器，会让接下来的thenAccept线程在线程池中执行
                // 取消sleep定时器，会让接下来的thenAccept线程在main线程中执行
                // 原理：thenAccept/thenAcceptAsync提交任务的时候会首先判断上一个阶段是否已经执行完成，即是否已完成执行
                // 如果上一阶段已经执行完成，则使用当前线程(这里的当前线程是指main线程)执行thenAccept/thenAcceptAsync任务
                // 如果上一阶段没有执行完成，会将thenAccept/thenAcceptAsync提交的任务存放到队列中去，由线程池统一调度

                // thenAccept与thenAcceptAsync区别，
                //  前者在上一阶段没有执行完成时，使用的默认的线程池，
                //  后者在上一节点没有执行完成时，使用的执行的线程池执行
                System.out.println(Thread.currentThread().getName() + " : 第一阶段:" + 1);
                return 1;
            }
        });

        future1.thenAccept(new Consumer<Integer>() {
            @SneakyThrows
            @Override
            public void accept(Integer result) {
                System.out.println(Thread.currentThread().getName() + " : 第二阶段:" + result);
            }
        });

        Thread.currentThread().join();
    }
}
