package com.snowbuffer.study.java.common.excel2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 15:52
 */
@Slf4j
public class ExcelExecutors {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            1000L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(50), new DefaultThreadFactory(), new DefaultRejectedExecutionHandler());


    public static <T> Long execute(AbstractImportExcel<T> excel, String absolutePath) {
        Long taskId = excel.init(absolutePath);
        executor.execute(excel);
        return taskId;
    }

    public static void close() {
        log.info("正在关闭ExcelExecutors服务...");
        executor.shutdown();
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private AtomicInteger counter = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(String.format("excel-import-thread-%s", counter.incrementAndGet()));
            return thread;
        }
    }

    private static class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
        public DefaultRejectedExecutionHandler() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RuntimeException("当前任务量过大，请稍后重试");
        }
    }
}
