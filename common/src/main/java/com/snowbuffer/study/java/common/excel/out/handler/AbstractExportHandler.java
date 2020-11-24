package com.snowbuffer.study.java.common.excel.out.handler;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.snowbuffer.study.java.common.excel.enums.ExcelVersion;
import com.snowbuffer.study.java.common.excel.enums.FlowStatus;
import com.snowbuffer.study.java.common.excel.out.*;
import com.snowbuffer.study.java.common.excel.support.BasePageQuery;
import com.snowbuffer.study.java.common.excel.support.MelotPageResult;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 15:24
 */
@Slf4j
public abstract class AbstractExportHandler extends ExportHandlerSupport implements ExportHandler {

    @Override
    public ExportContext init(Long taskId, ExportConfig exportConfig) {
        ExportContext exportContext = new ExportContext();
        exportContext.setExportConfig(exportConfig);
        exportContext.setTaskId(taskId);

        // 设置excel版本
        exportContext.setExcelVersion(ExcelVersion.getByFilePath(exportConfig.getDownLoadFileName()));

        exportContext.setPhaseSuccess(true);

        // 设置日志模板
        exportContext.setMessage(String.format(DEFAULT_MESSAGE_TEMPLATE, exportConfig.getUserId(), taskId));
        exportContext.setMessageWithPlaceHodler2(
                String.format(DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_2, exportConfig.getUserId(), taskId) + "%s");
        exportContext.setMessageWithPlaceHodler1(
                String.format(DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_1, exportConfig.getUserId(), taskId));
        return exportContext;
    }

    @Override
    public void collectExportData(ExportContext context) {
        Long taskId = context.getTaskId();
        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();
        boolean debug = context.getExportConfig().isDebug();

        // 切换任务状态： 任务已进入队列 -> 导出中
        switchStatus(taskId, FlowStatus.EXPORT_WAITING_QUEUE, FlowStatus.EXPORTING, null, null);

        try {
            List<SheetWorker<?>> sheetWorkers = context.getExportConfig().getSheetWorkers();
            for (SheetWorker<?> sheetWorker : sheetWorkers) {
                if (debug) {
                    log.info(messageWithPlaceHodler1, String.format("开始搜集数据：sheetName:%s",
                            sheetWorker.getSheetName()));
                }
                // 获取业务执行器
                Function<BasePageQuery, MelotPageResult<?>> dataSupplierWithPage
                        = sheetWorker.getDataSupplierWithPage();
                // 按分页
                if (dataSupplierWithPage != null) {
                    // 先取第一页数据
                    BasePageQuery basePageQuery = sheetWorker.getBasePageQuery();
                    basePageQuery.setOffset(0);
                    MelotPageResult<?> pageResult = fetchPage(basePageQuery, dataSupplierWithPage);
                    if (debug) {
                        log.info(messageWithPlaceHodler1, String.format("已搜集第[1]页数据：sheetName:%s, pageData.size:%s",
                                sheetWorker.getSheetName(), pageResult.getList().size()));
                    }
                    // 存入数据
                    context.getBizDataMap().putIfAbsent(sheetWorker, new LinkedList<>());
                    context.getBizDataMap().get(sheetWorker).addAll(
                            pageResult.getList() == null ? Collections.emptyList() : pageResult.getList());

                    // 重新计算页数，如果大于1页，分批查询数据
                    int pageNum = calcPageNum(pageResult.getTotal(), pageResult.getPageSize());
                    if (pageNum > 1) {
                        if (debug) {
                            log.info(messageWithPlaceHodler1,
                                    String.format("存在多页数据：sheetName:%s,总页数:%s, 每页大小:%s",
                                            sheetWorker.getSheetName(), pageNum, pageResult.getPageSize()));
                        }
                        for (int i = 1; i < pageNum; i++) {
                            int actualNum = i + 1;
                            rebuildPageQuery(basePageQuery, pageResult.getPageSize(), i * pageResult.getPageSize());
                            // 获取下一页数据
                            pageResult = fetchPage(basePageQuery, dataSupplierWithPage);
                            if (debug) {
                                log.info(messageWithPlaceHodler1,
                                        String.format("已搜集第[%s]页数据：sheetName:%s, pageData.size:%s",
                                                actualNum, sheetWorker.getSheetName(), pageResult.getList().size()));
                            }
                            // 暂存数据
                            context.getBizDataMap().putIfAbsent(sheetWorker, new LinkedList<>());
                            context.getBizDataMap().get(sheetWorker).addAll(pageResult.getList());
                        }
                    }
                } else {
                    // 按单页
                    Supplier<List<?>> dataSupplierWithoutPage = sheetWorker.getDataSupplierWithoutPage();
                    if (dataSupplierWithoutPage != null) {
                        List<?> list = dataSupplierWithoutPage.get();
                        // 存入数据
                        context.getBizDataMap().putIfAbsent(sheetWorker, new LinkedList<>());
                        context.getBizDataMap().get(sheetWorker).addAll(
                                list == null ? Collections.emptyList() : list);
                    }
                }

            }
        } catch (Exception e) {
            context.setPhaseSuccess(false);
            log.error(String.format(messageWithPlaceHodler2, "搜集数据发生未知异常"), e);
        }
    }

    private MelotPageResult<?> fetchPage(
            BasePageQuery basePageQuery, Function<BasePageQuery, MelotPageResult<?>> dataSupplierWithPage) {
        return dataSupplierWithPage.apply(basePageQuery);
    }

    private int calcPageNum(int total, Integer pageSize) {
        int tempPageNum = total % pageSize;
        return tempPageNum == 0 ? total / pageSize : (total / pageSize + 1);
    }

    private void rebuildPageQuery(BasePageQuery query, Integer pageSize, Integer offset) {
        query.setNeedTotalSize(true);
        query.setPageSize(pageSize);
        query.setOffset(offset);
    }

    @Override
    public void failureByCollectExportData(ExportContext context) {
        Long taskId = context.getTaskId();
        // 切换任务状态： 导出中 -> 导出失败
        switchStatus(taskId, FlowStatus.EXPORTING, FlowStatus.EXPORT_FAILURE, null, null);
    }

    @Override
    public void exportData(ExportContext context) {
        boolean debug = context.getExportConfig().isDebug();
        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();

        ExcelVersion excelVersion = context.getExcelVersion();
        if (debug) {
            log.info(messageWithPlaceHodler1, String.format("当前导出excelVersion:%s", excelVersion.getSuffix()));
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            switch (excelVersion) {
                case EXCEL_CSV:
                    genCsv(context);
                case EXCEL_XLSX:
                    genExcel(context, ExcelTypeEnum.XLSX, out);
                    break;
                case EXCEL_XLS:
                    genExcel(context, ExcelTypeEnum.XLS, out);
                    break;
                case UNKNOWN:
                    throw new RuntimeException("未知excel导出版本");
                default:
                    throw new RuntimeException("未知excel导出版本");
            }
        } catch (Exception e) {
            context.setPhaseSuccess(false);
            log.error(String.format(messageWithPlaceHodler2, "XLS|XLSX生成文件发生未知异常"), e);
        }

        try {
            // 清空业务数据 减少资源占用
            context.setBizDataMap(null);
            if (context.isPhaseSuccess()) {
                try {
                    String ossUrl = upload(messageWithPlaceHodler1, context.getExportConfig().getDownLoadFileName(), out.toByteArray());
                    context.setStoreUrl(ossUrl);
                } catch (Exception e) {
                    context.setPhaseSuccess(false);
                    log.error(String.format(messageWithPlaceHodler2, "XLS|XLSX上传发生未知异常"), e);
                }
            }
        } finally {
            closeAllStream(messageWithPlaceHodler2, out);
        }
    }

    @Override
    public void failureByExportData(ExportContext context) {
        // 修改数据库任务状态
        Long taskId = context.getTaskId();
        // 切换任务状态： 导出中 -> 导出失败
        switchStatus(taskId, FlowStatus.EXPORTING, FlowStatus.EXPORT_FAILURE, null, null);
    }

    @Override
    public void exportDataSuccess(ExportContext context) {
        // 修改数据库任务状态 => 成功
        Long taskId = context.getTaskId();
        String storeUrl = context.getStoreUrl();
        // 切换任务状态： 导出中 -> 导出成功
        switchStatus(taskId, FlowStatus.EXPORTING, FlowStatus.EXPORT_SUCCESS, storeUrl, null);
    }

    @Override
    public void failureBySetpPhaseExcpetion(
            ExportContext context, Exception exception, ExportEngine.StepProvider.StepPhase stepPhase) {
        Long taskId = context.getTaskId();
        // 切换任务状态： 导出中 -> 导出失败
        switchStatus(taskId, FlowStatus.EXPORTING, FlowStatus.EXPORT_FAILURE, null, null);

    }

    @Override
    public void failureByGlobalExcpetion(ExportContext context, Exception exception) {
        Long taskId = context.getTaskId();
        // 切换任务状态： 导出中 -> 导出失败
        switchStatus(taskId, FlowStatus.EXPORTING, FlowStatus.EXPORT_FAILURE, null, null);
    }

    @Override
    public void close(ExportContext context) {

    }
}
