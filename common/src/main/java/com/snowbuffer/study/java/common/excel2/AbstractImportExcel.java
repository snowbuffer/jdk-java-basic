package com.snowbuffer.study.java.common.excel2;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.snowbuffer.study.java.common.excel2.listener.LifeListener;
import com.snowbuffer.study.java.common.excel2.listener.ObservableRunnable;
import com.snowbuffer.study.java.common.excel2.parser.ExcelParser;
import com.snowbuffer.study.java.common.excel2.upload.UploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 11:03
 */
@Slf4j
public abstract class AbstractImportExcel<T> extends ObservableRunnable<T> {

    private ExcelParser<T> parser;

    private UploadRepository repository;

    private Long taskId;

    private String absolutePath;

    public AbstractImportExcel(LifeListener<T> lifeListener, UploadRepository repository) {
        super(lifeListener);
        Class<T> clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(AbstractImportExcel.this.getClass(), AbstractImportExcel.class);
        this.parser = ExcelConfigFactory.getDefaultParser(clazz);
        this.repository = repository;
    }

    public AbstractImportExcel(ExcelParser<T> parser, LifeListener<T> listener, UploadRepository repository) {
        super(listener);
        this.parser = parser;
        this.repository = repository;
    }

    public Long init(String absolutePath) {
        RunnableEvent<T> event = RunnableEvent.<T>builder().uniqueId(null).runnableState(RunnableState.INIT).dataList(null).cause(null).build();
        this.notifyChange(event);
        this.taskId = event.getUniqueId();
        this.absolutePath = absolutePath;
        return this.taskId;
    }

    @Override
    public void run() {
        try {
            this.execute(this.taskId, this.absolutePath);
        } catch (Exception e) {
            try {
                this.notifyChange(
                        RunnableEvent.<T>builder()
                                .uniqueId(taskId).runnableState(RunnableState.GLOBAL_UNKNOWN_EXCEPTION).dataList(null).cause(e).build());
            } catch (Exception e1) {
                log.error("taksId={},发生未知异常[兜底]", taskId, e);
            }
        } finally {
            try {
                FileUtil.del(absolutePath);
            } catch (IORuntimeException e) {
                log.error("清理用户上传文件失败,路径 => {}", absolutePath, e);
            }
        }
    }

    private void execute(Long taskId, String absolutePath) {
        // 解析
        List<T> dataList = Lists.newArrayList();
        try {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.PARSING).dataList(dataList).cause(null).build());
            dataList = parser.parse(absolutePath);
        } catch (Exception e) {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.PARSE_UNKNOWN_EXCEPTION).dataList(dataList).cause(e).build());
            return;
        }

        // 检查
        try {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.CHECKING).dataList(dataList).cause(null).build());
            if (!this.checkDataWithoutThrowException(dataList)) {
                // 检查失败，生成excel
                this.generateExcel(dataList);
                return;
            }
        } catch (Exception e) {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.CHECK_UNKNOWN_EXCEPTION).dataList(dataList).cause(e).build());
            return;
        }

        // 导入
        List<T> errorDataList;
        try {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.IMPORTING).dataList(dataList).cause(null).build());
            errorDataList = this.importDataWithoutThrowException(dataList);
            if (!CollectionUtils.isEmpty(errorDataList)) {
                // 导入结果存在错误数据，生成excel
                this.generateExcel(errorDataList);
                return;
            }
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.SUCCESS).dataList(dataList).cause(null).build());
        } catch (Exception e) {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.IMPORT_UNKNOWN_EXCEPTION).dataList(dataList).cause(e).build());
        }
    }

    private void generateExcel(List<T> dataList) {

        // 生成excel
        File destFile;
        try {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.WRITING_EXCEL_DATA).dataList(dataList).cause(null).build());
            destFile = this.writeExcelData(dataList);
        } catch (Exception e) {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.WRITING_EXCEL_DATA_UNKNOWN_EXCEPTION).dataList(dataList).cause(e).build());
            return;
        }

        if (log.isDebugEnabled()) {
            logErrorDataList(destFile);
        }

        // 上传excel
        try {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.UPLOADING).dataList(dataList).cause(null).build());
            String uploadUrl = this.upload(destFile);
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.UPLOADING_SUCCESS).dataList(dataList).uploadUrl(uploadUrl).cause(null).build());
        } catch (Exception e) {
            this.notifyChange(
                    RunnableEvent.<T>builder()
                            .uniqueId(taskId).runnableState(RunnableState.UPLOADING_UNKNOWN_EXCEPTION).dataList(dataList).cause(e).build());
        }


    }

    private void logErrorDataList(File destFile) {
        int counter = 0;
        Gson gson = new Gson();
        List<T> errorDataList = parser.parse(destFile.getAbsolutePath());
        for (T record : errorDataList) {
            log.debug("第[{}]条 record[错误记录] => {}", ++counter, gson.toJson(record));
        }
    }

    private String upload(File destFile) {
        return this.repository.upload(destFile);
    }

    private File writeExcelData(List<T> dataList) {
        return this.parser.write(dataList);
    }

    // 业务自己处理异常，此方法不要向上跑出业务异常，否则无法进入exel导出流程
    protected abstract boolean checkDataWithoutThrowException(List<T> dataList);

    // 业务自己处理异常，此方法不要向上跑出业务异常，否则无法进入exel导出流程
    protected abstract List<T> importDataWithoutThrowException(List<T> dataList);


}
