package com.snowbuffer.study.java.common.excel.out;

import com.google.common.collect.Lists;
import com.snowbuffer.study.java.common.excel.enums.ExportSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 11:16
 */
@Slf4j
@Service
public class ExportEngine {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1000L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(50), new DefaultThreadFactory(), new DefaultRejectedExecutionHandler());

    // 导出处理器
    private List<ExportHandler> exportHandlers;

    // 步骤提供器：主要用于定制任务的执行步骤
    private List<StepProvider> stepProviders = Lists.newArrayList();

    // 默认的步骤提供器
    private StepProvider DEFAULT_STEPPROVIDER = new DefaultStepProvider();

    public ExportEngine(@Autowired List<ExportHandler> exportHandlers) {
        Assert.isTrue(!CollectionUtils.isEmpty(exportHandlers), "没有设置ExportHandler");
        this.exportHandlers = exportHandlers;
    }

    public Long start(ExportConfig exportConfig) {
        // 创建任务
        Long taskId = createTask(exportConfig);
        // 提交任务
        submitTask(taskId, exportConfig);
        return taskId;
    }

    public void close() {
        log.info("正在执行shutdown线程池操作...");
        executor.shutdown();
    }

    private void submitTask(Long taskId, ExportConfig exportConfig) {
        String message = String.format(ExportHandler.DEFAULT_MESSAGE_TEMPLATE, exportConfig.getUserId(), taskId);
        executor.submit(() -> {
            for (ExportHandler exportHandler : exportHandlers) {
                if (exportHandler.support(exportConfig.getExportSource())) {
                    StepProvider stepProvider = findStepProvider(exportConfig.getExportSource());
                    if (stepProvider == null) {
                        log.info("{}, 未能找到合适的ExportStepProvider，切换成默认的DefaultExportStepProvider", message);
                        stepProvider = DEFAULT_STEPPROVIDER;
                    }
                    log.info("{}, 准备执行stepProvider:{}, exportHandler: {}",
                            message, stepProvider.getClass().getSimpleName(), exportHandler.getClass().getSimpleName());
                    stepProvider.process(exportHandler, taskId, exportConfig);
                    return;
                }
            }
            log.error("{}, 未能找到合适的exportHandler, 请检查配置是否存在问题", message);
        });
    }

    private StepProvider findStepProvider(ExportSource exportSource) {
        for (StepProvider stepProvider : stepProviders) {
            if (stepProvider.support(exportSource)) {
                return stepProvider;
            }
        }
        return null;
    }

    protected Long createTask(ExportConfig exportConfig) {
        return 123456L;
    }


    private static class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
        public DefaultRejectedExecutionHandler() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new ExcelExportTaskCountToMaxException("当前导出任务量过大，请稍后重试");
        }
    }

    private static class ExcelExportTaskCountToMaxException extends RuntimeException {
        public ExcelExportTaskCountToMaxException(String message) {
            super(message);
        }
    }

    private class DefaultThreadFactory implements ThreadFactory {

        private AtomicInteger counter = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(String.format("export-excel-thread-%s", counter.incrementAndGet()));
            return thread;
        }
    }

    public interface StepProvider {

        boolean support(ExportSource module);

        void process(ExportHandler exportHandler, Long taskId, ExportConfig exportConfig);

        enum StepPhase {
            INITING, COLLECTING, EXPORTING, SUCCESS, CLOSEING
        }
    }

    @Slf4j
    private static class DefaultStepProvider implements StepProvider {


        @Override
        public boolean support(ExportSource module) {
            return true;
        }

        @Override
        public void process(ExportHandler exportHandler, Long taskId, ExportConfig exportConfig) {
            String message = String.format(ExportHandler.DEFAULT_MESSAGE_TEMPLATE, exportConfig.getUserId(), taskId);
            ExportContext book = null;
            try {
                // INITING
                try {
                    log.info("[START] {}, init...", message);
                    book = exportHandler.init(taskId, exportConfig);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    exportHandler.failureBySetpPhaseExcpetion(book, e, StepProvider.StepPhase.INITING);
                    return;
                }

                // COLLECTING
                try {
                    log.info("[STARTING] {}, start collectExportData...", message);
                    exportHandler.collectExportData(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    exportHandler.failureBySetpPhaseExcpetion(book, e, StepProvider.StepPhase.COLLECTING);
                    return;
                }
                if (!book.isPhaseSuccess()) {
                    log.error("[END,FAILURE] {}, {} failureByCollectExportData...", message);
                    exportHandler.failureByCollectExportData(book);
                    return;
                }

                // CHECKING
                try {
                    log.info("[STARTING] {}, start exportData...", message);
                    exportHandler.exportData(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    exportHandler.failureBySetpPhaseExcpetion(book, e, StepProvider.StepPhase.EXPORTING);
                    return;
                }
                if (!book.isPhaseSuccess()) {
                    log.error("[END,FAILURE] {}, failureByExportData...", message);
                    exportHandler.failureByExportData(book);
                    return;
                }

                // SUCCESS
                try {
                    log.info("[STARTING] {}, start exportDataSuccess...", message);
                    exportHandler.exportDataSuccess(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    exportHandler.failureBySetpPhaseExcpetion(book, e, StepProvider.StepPhase.SUCCESS);
                }
            } catch (Exception e) {
                log.error(String.format("[END,FAILURE,兜底异常] %s,发生未知异常", message), e);
                exportHandler.failureByGlobalExcpetion(book, e);
            } finally {
                try {
                    log.info("[END,CLOSE] {}, close...", message);
                    exportHandler.close(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    exportHandler.failureBySetpPhaseExcpetion(book, e, StepProvider.StepPhase.CLOSEING);
                }
            }
        }
    }
}
