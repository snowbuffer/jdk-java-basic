package com.snowbuffer.study.java.common.excel.in;

import com.google.common.collect.Lists;
import com.snowbuffer.study.java.common.excel.enums.ImportSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: 导入引擎
 *
 * @author cjb
 * @since 2020-11-11 22:14
 */
@Slf4j
@Component
public class ImportEngine {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1000L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(50), new DefaultThreadFactory(), new DefaultRejectedExecutionHandler());

    // 导入处理器
    private List<ImportHandler<?>> importHandlers;

    // 步骤提供器：主要用于定制任务的执行步骤
    private List<StepProvider> stepProviders = Lists.newArrayList();

    // 默认的步骤提供器：
    //    流程1：init -> read -> checkData[falured] -> close
    //    流程2：init -> read -> checkData[success] -> importData -> close
    private StepProvider DEFAULT_STEPPROVIDER = new DefaultStepProvider();

    public ImportEngine(@Autowired List<ImportHandler<?>> importHandlers) {
        Assert.isTrue(!CollectionUtils.isEmpty(importHandlers), "没有设置ImportHandler");
        this.importHandlers = importHandlers;
    }

    public Long start(ImportConfig importConfig) {
        // 创建任务
        Long taskId = createTask(importConfig);
        // 提交任务
        submitTask(taskId, importConfig);
        return taskId;
    }

    public void close() {
        log.info("正在执行shutdown线程池操作...");
        executor.shutdown();
    }

    protected Long createTask(ImportConfig importConfig) {
        return 123L;
    }

    private void submitTask(Long taskId, ImportConfig importConfig) {
        String message = String.format(ImportHandler.DEFAULT_MESSAGE_TEMPLATE, importConfig.getUserId(), taskId);
        executor.submit(() -> {
            for (ImportHandler<?> importHandler : importHandlers) {
                if (importHandler.support(importConfig.getImportSource())) {
                    StepProvider stepProvider = findStepProvider(importConfig.getImportSource());
                    if (stepProvider == null) {
                        log.info("{}, 未能找到合适的ImportStepProvider，切换成默认的ImportDefaultStepProvider", message);
                        stepProvider = DEFAULT_STEPPROVIDER;
                    }
                    log.info("{}, 准备执行stepProvider:{}, importHandler: {}",
                            message, stepProvider.getClass().getSimpleName(), importHandler.getClass().getSimpleName());
                    stepProvider.process(importHandler, taskId, importConfig);
                    return;
                }
            }
            log.error("{}, 未能找到合适的importHandler, 请检查配置是否存在问题", message);
        });
    }

    private StepProvider findStepProvider(ImportSource importSource) {
        for (StepProvider stepProvider : stepProviders) {
            if (stepProvider.support(importSource)) {
                return stepProvider;
            }
        }
        return null;
    }

    @Slf4j
    private static class DefaultStepProvider implements StepProvider {

        @Override
        public boolean support(ImportSource module) {
            return true;
        }

        @Override
        public void process(ImportHandler<?> importHandler, Long taskId, ImportConfig importConfig) {
            String message = String.format(ImportHandler.DEFAULT_MESSAGE_TEMPLATE, importConfig.getUserId(), taskId);
            ImportContext book = null;
            try {
                // INITING
                try {
                    log.info("[START] {}, init...", message);
                    book = importHandler.init(taskId, importConfig);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    importHandler.failureBySetpPhaseExcpetion(book, e, StepPhase.INITING);
                    return;
                }

                // READING
                try {
                    log.info("[STARTING] {}, start read: {}...", message, importConfig.getFilePath());
                    importHandler.read(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    importHandler.failureBySetpPhaseExcpetion(book, e, StepPhase.READING);
                    return;
                }
                if (!book.isPhaseSuccess()) {
                    log.error("[END,FAILURE] {}, {} failureByRead...", message, importConfig.getFilePath());
                    importHandler.failureByRead(book);
                    return;
                }

                // CHECKING
                try {
                    log.info("[STARTING] {}, start checkDataBeforeImport...", message);
                    importHandler.checkDataBeforeImport(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    importHandler.failureBySetpPhaseExcpetion(book, e, StepPhase.CHECKING);
                    return;
                }
                if (!book.isPhaseSuccess()) {
                    log.error("[END,FAILURE] {}, failureByCheckDataBeforeImport...", message);
                    importHandler.failureByCheckDataBeforeImport(book);
                    return;
                }

                // IMPORTING
                try {
                    log.info("[STARTING] {}, start importData...", message);
                    importHandler.importData(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    importHandler.failureBySetpPhaseExcpetion(book, e, StepPhase.IMPORTING);
                    return;
                }
                if (!book.isPhaseSuccess()) {
                    log.error("[END,FAILURE] {}, failureByImportData...", message);
                    importHandler.failureByImportData(book);
                    return;
                }

                // SUCCESS
                try {
                    log.info("[END,SUCCESS] {}, importDataSuccess...", message);
                    importHandler.importDataSuccess(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    importHandler.failureBySetpPhaseExcpetion(book, e, StepPhase.IMPORTING);
                    return;
                }
            } catch (Exception e) {
                log.error(String.format("[END,FAILURE,兜底异常] %s,发生未知异常", message), e);
                importHandler.failureByGlobalExcpetion(book, e);
            } finally {
                try {
                    log.info("[END,CLOSE] {}, {}, close...", message, importConfig.getFilePath());
                    importHandler.close(book);
                } catch (Exception e) {
                    log.error(String.format("[END,FAILURE] %s,发生未知异常", message), e);
                    importHandler.failureBySetpPhaseExcpetion(book, e, StepPhase.CLOSEING);
                }
            }
        }
    }

    private class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
        public DefaultRejectedExecutionHandler() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new ExcelImportTaskCountToMaxException("当前导入任务量过大，请稍后重试");
        }
    }

    private static class ExcelImportTaskCountToMaxException extends RuntimeException {
        public ExcelImportTaskCountToMaxException(String message) {
            super(message);
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private AtomicInteger counter = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(String.format("import-excel-thread-%s", counter.incrementAndGet()));
            return thread;
        }
    }

    public interface StepProvider {

        boolean support(ImportSource module);

        void process(ImportHandler<?> importHandler, Long taskId, ImportConfig importConfig);

        enum StepPhase {
            INITING, READING, CHECKING, IMPORTING, SUCCESS, CLOSEING
        }
    }
}
