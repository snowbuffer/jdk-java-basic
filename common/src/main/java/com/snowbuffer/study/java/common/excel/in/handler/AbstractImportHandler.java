package com.snowbuffer.study.java.common.excel.in.handler;

import com.alibaba.excel.metadata.RowErrorModel;
import com.google.common.collect.Lists;
import com.snowbuffer.study.java.common.excel.enums.ExcelVersion;
import com.snowbuffer.study.java.common.excel.enums.FlowStatus;
import com.snowbuffer.study.java.common.excel.enums.ImportSource;
import com.snowbuffer.study.java.common.excel.in.ImportConfig;
import com.snowbuffer.study.java.common.excel.in.ImportContext;
import com.snowbuffer.study.java.common.excel.in.ImportEngine;
import com.snowbuffer.study.java.common.excel.in.ImportHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-11 23:37
 */
@Slf4j
public abstract class AbstractImportHandler<T extends RowErrorModel> extends ImportHandlerSupport implements ImportHandler<T> {

    private static final String REMARK_PATTERN = "%s#%s#%s"; // total#success#failure

    @Override
    public ImportContext<T> init(Long taskId, ImportConfig importConfig) {


        ImportContext<T> container = new ImportContext<>();
        container.setImportConfig(importConfig);
        container.setTaskId(taskId);

        ExcelVersion excelVersion = ExcelVersion.getByFilePath(importConfig.getFilePath());
        Assert.isTrue(ExcelVersion.UNKNOWN != excelVersion, "未知excel文件类型");
        container.setExcelVersion(excelVersion);

        container.setSheetDataList(Lists.newLinkedList());
        container.setSheetLastHeadList(Lists.newArrayList());

        // 预制模板设置
        ImportSource.PrepareTemplate prepareTemplate = importConfig.getImportSource().getPrepareTemplate();
        container.setSheetLastTitleRownum(prepareTemplate.getDataFirstRownum());
        container.setPrepareExcelTemplateAddress(prepareTemplate.getAddress());
        container.setSheetClass(prepareTemplate.getSheetClass());
        container.setSheetNum(importConfig.getSheetNum());

        container.setPhaseSuccess(true);

        // 设置日志模板
        container.setMessage(String.format(DEFAULT_MESSAGE_TEMPLATE, importConfig.getUserId(), taskId));
        container.setMessageWithPlaceHodler1(
                String.format(DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_1, importConfig.getUserId(), taskId));
        container.setMessageWithPlaceHodler2(
                String.format(DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_2, importConfig.getUserId(), taskId) + "%s");
        return container;
    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void read(ImportContext<T> context) {
        Long taskId = context.getTaskId();
        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();
        log.info(messageWithPlaceHodler1, String.format("切换excel任务状态: %s/%s", FlowStatus.IMPORT_WAITING_QUEUE,
                FlowStatus.IMPORT_PARSEING));

        // 切换任务状态： 任务已进入队列 -> 解析excel中
        switchStatus(taskId, FlowStatus.IMPORT_WAITING_QUEUE, FlowStatus.IMPORT_PARSEING, null, null);

        try {
            SheetParseResult<T> parseResult = parseExcel(
                    context, context.getImportConfig().getFilePath(), context.getSheetNum(),
                    context.getSheetLastTitleRownum(), context.getSheetClass());

            context.setPhaseSuccess(parseResult.isSuccess());
            context.setSheetName(parseResult.getSheetName());
            context.setSheetDataList(parseResult.getBizDataList());
            context.setSheetLastHeadList(parseResult.getLastHeadList());
        } catch (Exception e) {
            context.setPhaseSuccess(false);
            log.error(String.format(messageWithPlaceHodler2, "解析excel发生未知异常"), e);
        }

    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void failureByRead(ImportContext<T> context) {
        ImportConfig importConfig = context.getImportConfig();
        Long taskId = context.getTaskId();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();

        String ossUrl = null;
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(
                    new BufferedInputStream(new FileInputStream(importConfig.getFilePath())));
            ossUrl = upload(context.getMessageWithPlaceHodler1(), importConfig.getFileName(), bytes);
        } catch (Exception e) {
            log.error(String.format(messageWithPlaceHodler2, "解析excel失败 => 上传源文件至存储服务发生未知异常"), e);
        } finally {
            // 切换任务状态： 解析excel中 -> 解析excel失败
            switchStatus(taskId, FlowStatus.IMPORT_PARSEING, FlowStatus.IMPORT_PARSEING_FAILURE, ossUrl,
                    String.format(REMARK_PATTERN, 0, 0, 0));
        }
    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void checkDataBeforeImport(ImportContext<T> context) {
        Long taskId = context.getTaskId();

        // 切换任务状态： 解析excel中 -> 数据校验中
        switchStatus(taskId, FlowStatus.IMPORT_PARSEING, FlowStatus.IMPORT_PARAM_CHECKING, null, null);

        // 遍历业务数据
        SheetDataCheckResult iteraterResult = rowDataAccessor(
                context, context.getSheetDataList(),
                context.getSheetLastTitleRownum(), this::checkRecord);

        // 设置结果
        context.setPhaseSuccess(iteraterResult.getSuccess());
        context.setSheetDataFailureCount(iteraterResult.getBizFailureCount());

    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void failureByCheckDataBeforeImport(ImportContext<T> context) {

        Long taskId = context.getTaskId();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();

        // 组装重写excel信息
        SheetDataRewriteConfig<T> sheetDataRewriteConfig =
                buildRewriteConfig(context, 1, context.getSheetClass(), context.getSheetDataList());

        ByteArrayOutputStream out = null;
        String ossUrl = null;
        try {
            // 生成excel
            out = genExcel(context, context.getPrepareExcelTemplateAddress(), context.getExcelVersion(), sheetDataRewriteConfig);

            // 上传excel
            ossUrl = upload(context.getMessageWithPlaceHodler1(), context.getImportConfig().getFileName(), out.toByteArray());
        } catch (Exception e) {
            log.error(String.format(messageWithPlaceHodler2, "数据校验失败 => 生成错误业务数据文件发生未知异常"), e);
        } finally {
            closeAllStream(context.getMessageWithPlaceHodler2(), out);
            int[] rs = getTotalSuccessFailure(context);
            // 切换任务状态： 数据校验中 -> 数据校验失败
            switchStatus(
                    taskId, FlowStatus.IMPORT_PARAM_CHECKING, FlowStatus.IMPORT_PARAM_CHECKING_FAILURE,
                    ossUrl, String.format(REMARK_PATTERN, rs[0], rs[1], rs[2]));
        }
    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void importData(ImportContext<T> context) {

        Long taskId = context.getTaskId();

        // 切换任务状态： 数据校验中 -> 数据入库中
        switchStatus(taskId, FlowStatus.IMPORT_PARAM_CHECKING, FlowStatus.IMPORT_DB_IMPORTING, null, null);

        // 遍历业务数据
        SheetDataCheckResult iteraterResult = rowDataAccessor(
                context, context.getSheetDataList(),
                context.getSheetLastTitleRownum(), this::importRecord);

        // 设置结果
        context.setPhaseSuccess(iteraterResult.getSuccess());
        context.setSheetDataFailureCount(iteraterResult.getBizFailureCount());
    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void failureByImportData(ImportContext<T> context) {

        Long taskId = context.getTaskId();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();

        // 抽取失败的业务数据列表
        List<T> failureDataList = extractFailureDataList(context.getSheetDataList());

        // 组装重写excel信息
        SheetDataRewriteConfig<T> sheetDataRewriteConfig =
                buildRewriteConfig(context, 1, context.getSheetClass(), failureDataList);

        ByteArrayOutputStream out = null;
        String ossUrl = null;
        try {
            // 生成excel
            out = genExcel(context, context.getPrepareExcelTemplateAddress(), context.getExcelVersion(), sheetDataRewriteConfig);

            // 上传excel
            ossUrl = upload(context.getMessageWithPlaceHodler1(), context.getImportConfig().getFileName(), out.toByteArray());
        } catch (Exception e) {
            log.error(String.format(messageWithPlaceHodler2, "数据导入阶段失败 => 生成错误业务数据文件发生未知异常"), e);
        } finally {
            closeAllStream(context.getMessageWithPlaceHodler2(), out);
            int[] rs = getTotalSuccessFailure(context);
            // 切换任务状态： 数据入库中 -> 导入成功(部分落库)
            switchStatus(
                    taskId, FlowStatus.IMPORT_DB_IMPORTING, FlowStatus.IMPORT_SUCCESS_PART,
                    ossUrl, String.format(REMARK_PATTERN, rs[0], rs[1], rs[2]));
        }
    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void importDataSuccess(ImportContext<T> context) {
        Long taskId = context.getTaskId();
        int total = context.getSheetDataList().size();
        int failure = 0;
        // 切换任务状态： 数据入库中 -> 导入成功(全部落库)
        switchStatus(
                taskId, FlowStatus.IMPORT_DB_IMPORTING, FlowStatus.IMPORT_SUCCESS_FULL,
                null, String.format(REMARK_PATTERN, total, total, failure));
    }

    // Note: 99%不会走这个地方，每个环节{init,check,import} 都做了try-catch,这里只是从程序的健壮性角度考虑
    @Override
    public void failureBySetpPhaseExcpetion(
            ImportContext<T> context, Exception exception, ImportEngine.StepProvider.StepPhase stepPhase) {

        Long taskId = context.getTaskId();
        String message = context.getMessage();

        switch (stepPhase) {
            case INITING:
                // 切换任务状态： 解析excel中 -> 解析excel失败
                switchStatus(
                        taskId, FlowStatus.IMPORT_PARSEING, FlowStatus.IMPORT_PARSEING_FAILURE,
                        null, String.format(REMARK_PATTERN, 0, 0, 0));
                break;
            case READING:
                // 切换任务状态： 解析excel中 -> 解析excel失败
                switchStatus(
                        taskId, FlowStatus.IMPORT_PARSEING, FlowStatus.IMPORT_PARSEING_FAILURE,
                        null, String.format(REMARK_PATTERN, 0, 0, 0));
                break;
            case CHECKING:
                // 切换任务状态： 数据校验中 -> 数据校验失败
                switchStatus(
                        taskId, FlowStatus.IMPORT_PARAM_CHECKING, FlowStatus.IMPORT_PARAM_CHECKING_FAILURE,
                        null, String.format(REMARK_PATTERN, 0, 0, 0));
                break;
            case IMPORTING:
                // 切换任务状态： 数据入库中 -> 导入成功(部分落库)
                switchStatus(
                        taskId, FlowStatus.IMPORT_DB_IMPORTING, FlowStatus.IMPORT_SUCCESS_PART,
                        null, String.format(REMARK_PATTERN, 0, 0, 0));
                break;
            case SUCCESS:
                break;
            case CLOSEING:
                break;
            default:
                log.error(message, exception);
        }
    }

    @Override
    public void failureByGlobalExcpetion(ImportContext<T> context, Exception exception) {
//        log.error(String.format("[END,FAILURE,兜底异常] %s,发生未知异常", context.getMessage()), exception);
    }

    // 该环节需要自己处理异常，不建议异常外抛，外部异常处理仅做兜底
    @Override
    public void close(ImportContext<T> context) {
        if (context != null) {
            String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
            String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();
            if (context.getImportConfig().isDebug()) {
                log.warn(messageWithPlaceHodler1, "开始删除文件, debug模式下不删除原始文件");
                return;
            }
            // 清除excel原始文件
            String sourceFile = context.getImportConfig().getFilePath();
            if (!StringUtils.isEmpty(sourceFile)) {

                try {
                    log.info(messageWithPlaceHodler1, String.format("开始删除文件, sourceFile:%s", sourceFile));
                    Files.deleteIfExists(Paths.get(sourceFile));
                } catch (IOException e) {
                    log.info(String.format(messageWithPlaceHodler2, String.format("删除文件发生未知异常, sourceFile:%s", sourceFile)), e);
                }
            }
        }
    }

    protected abstract boolean checkRecord(
            Map<String, Object> bizParamMap, T recordVO,
            List<T> sheetDataList, Map<Object, T> visitedMap);

    protected abstract boolean importRecord(
            Map<String, Object> bizParamMap, T recordVO,
            List<T> sheetDataList, Map<Object, T> visitedMap);

}
