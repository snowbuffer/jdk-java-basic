package com.snowbuffer.study.java.common.excel.in.handler;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.RowErrorModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.snowbuffer.study.java.common.excel.enums.ExcelVersion;
import com.snowbuffer.study.java.common.excel.in.ImportConfig;
import com.snowbuffer.study.java.common.excel.in.ImportContext;
import com.snowbuffer.study.java.common.excel.in.strategy.RowAccessorStrategy;
import com.snowbuffer.study.java.common.excel.support.AbstractHandlerSupport;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-12 00:09
 */
@Slf4j
public abstract class ImportHandlerSupport extends AbstractHandlerSupport {

    /**
     * 解析excel
     */
    protected <T extends RowErrorModel> SheetParseResult<T> parseExcel(
            ImportContext<T> context, String sourceFilePath, Integer sheetNum, Integer lastHeadNum,
            Class<? extends RowErrorModel> clazz) throws IOException {

        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
        ExcelListener<T> excelListener = new ExcelListener<>(log, messageWithPlaceHodler1);

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(sourceFilePath));
            // 解析文件
            EasyExcelFactory.readBySax(inputStream, new com.alibaba.excel.metadata.Sheet(
                    sheetNum, lastHeadNum, clazz), excelListener);
        } catch (IOException e) {
            throw e;
        } finally {
            closeAllStream(context.getMessageWithPlaceHodler2(), inputStream);
        }

        SheetParseResult<T> rs = new SheetParseResult<>();
        rs.setBizDataList(excelListener.getData());
        rs.setSheetName(excelListener.getSheetName());
        rs.setSuccess(excelListener.isParseSucceed());
        rs.setLastHeadList(excelListener.getLastHeadList());
        return rs;
    }

    /**
     * 遍历行记录
     */
    protected <T extends RowErrorModel> SheetDataCheckResult rowDataAccessor(
            ImportContext<T> context, List<T> bizDataList, Integer lastHeadNum,
            RowAccessorStrategy<T> rowAccessorStrategy) {

        ImportConfig importConfig = context.getImportConfig();
        boolean debug = importConfig.isDebug();
        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();
        int lineNumber = lastHeadNum;

        if (debug) {
            log.info(messageWithPlaceHodler1, String.format("业务数据从第%s开始", lastHeadNum));
        }

        // 统一设置行号
        for (T rowErrorModel : bizDataList) {
            rowErrorModel.setLineNumber(lineNumber);
            lineNumber++;
        }

        // 开始逐行数据检查
        SheetDataCheckResult sheetDataCheckResult = new SheetDataCheckResult();
        Map<Object, T> visitedMap = Maps.newHashMap();
        for (T rowRecord : bizDataList) {

            if (debug) {
                log.info(messageWithPlaceHodler1, String.format("正在检查第 %s 行的业务数据",
                        rowRecord.getLineNumber()));
            }

            if (rowRecord.getErrorMap().size() > 0) {
                // 如果已经被标记为失败的数据，不在进行visit操作
                log.warn(messageWithPlaceHodler1, String.format("第 %s 行的业务数据之前已经被标记为失败数据，因此skipped",
                        rowRecord.getLineNumber()));
                continue;
            }

            try {
                if (!rowAccessorStrategy.visit(
                        importConfig.getBizParamMap(), rowRecord, context.getSheetDataList(), visitedMap)) {
                    if (debug) {
                        log.error(messageWithPlaceHodler1, String.format("第 %s 行的业务数据检查不通过," +
                                "业务已经设置异常信息", rowRecord.getLineNumber()));
                    }
                    incrementFailureCount(sheetDataCheckResult);
                }
            } catch (Exception e) {
                log.error(String.format(messageWithPlaceHodler2, String.format("第 %s 行的业务数据检查不通过, " +
                        "发生未知异常，系统自动设置exceptionMessage", rowRecord.getLineNumber()), e));
                incrementFailureCount(sheetDataCheckResult);
                rowRecord.addBizError(e.getMessage());
            }
        }
        return sheetDataCheckResult;
    }

    /**
     * 根据模板文件生成excel
     */
    protected <T extends RowErrorModel> ByteArrayOutputStream genExcel(
            ImportContext<T> context, String prepareExcelTemplateAddress, ExcelVersion excelVersion,
            SheetDataRewriteConfig<T> sheetDataRewriteConfig) throws Exception {

        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();

        // 定位待回写的模板文件
        String resourcePath = prepareExcelTemplateAddress + "." + excelVersion.getSuffix();
        String path = this.getClass().getClassLoader().getResource(resourcePath).getPath();
        log.info(messageWithPlaceHodler1, String.format("已定位模板文件地址:%s", path));

        // 构建回写操作
        ByteArrayOutputStream out;
        ExcelWriter writer = null;
        try {
            out = new ByteArrayOutputStream();
            writer = EasyExcelFactory.getWriterWithTempAndHandler(
                    new FileInputStream(path),
                    out,
                    excelVersion == ExcelVersion.EXCEL_XLS ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX,
                    true,
                    null);

            Integer sheetNum = sheetDataRewriteConfig.getSheetNum();
            Integer lastHeadNum = sheetDataRewriteConfig.getLastHeadNum();
            String sheetName = sheetDataRewriteConfig.getSheetName();
            Class clazz = sheetDataRewriteConfig.getClazz();
            List<T> bizDataList = sheetDataRewriteConfig.getBizDataList();

            Sheet sheet = new Sheet(sheetNum, lastHeadNum);
            sheet.setSheetName(sheetName);
            sheet.setStartRow(lastHeadNum - 1);
            sheet.setClazz(clazz);
            if (sheetDataRewriteConfig.getNeedReWriteDyncHead()) {
                sheet.setStartRow(lastHeadNum - 2);
                List<List<Object>> headDataList = Lists.newArrayList();
                List<String> dyncHeadList = sheetDataRewriteConfig.getDyncHeadList();
                List<Object> newDyncHeadList = Lists.newArrayList();
                for (int i = 0; i < dyncHeadList.size() - 1; i++) {
                    newDyncHeadList.add(dyncHeadList.get(i));
                }
                headDataList.add(newDyncHeadList);
                writer.write1(headDataList, sheet);

                // 重新构建javaBean模型
                WriteContext writeContext = writer.exposeExcelContext();
                ExcelHeadProperty excelHeadProperty = writeContext.getExcelHeadProperty();
                excelHeadProperty.rebuildExcelHeadPropertyForDyncCol2(newDyncHeadList);
            }

            log.info(messageWithPlaceHodler1,
                    String.format("开始重新写入模板文件数据:sheet=%s;titleRownum=%s;beanName=%s;dataSize=%s", sheetName,
                            lastHeadNum, clazz.getName(), bizDataList.size()));

            writer.write(bizDataList, sheet);

            Map columnWidthMap = sheetDataRewriteConfig.getColumnWidthMap();
            if (columnWidthMap.size() > 0) {
                writer.setColumnWidth(columnWidthMap, 10);
            }
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
        return out;
    }

    /**
     * 抽取失败的记录
     */
    protected <T extends RowErrorModel> List<T> extractFailureDataList(List<T> sheetDataList) {
        List<T> rsList = Lists.newArrayList();
        for (T t : sheetDataList) {
            if (t instanceof RowErrorModel) {
                if (t.getErrorMap().size() > 0) {
                    rsList.add(t);
                }
            }
        }
        return rsList;
    }

    /**
     * 构建重写excel配置
     */
    protected <T extends RowErrorModel> SheetDataRewriteConfig<T> buildRewriteConfig(
            ImportContext<T> context, Integer sheetNum, Class<? extends RowErrorModel> clazz, List<T> dataList) {

        SheetDataRewriteConfig<T> config = new SheetDataRewriteConfig<>();
        config.setSheetNum(sheetNum);
        config.setSheetName(context.getSheetName());
        config.setLastHeadNum(context.getSheetLastTitleRownum());
        config.setClazz(clazz);
        config.setBizDataList(dataList);

        return config;
    }

    /**
     * 计算 total/success/failure
     */
    protected <T extends RowErrorModel> int[] getTotalSuccessFailure(ImportContext<T> context) {
        int[] rs = new int[3];
        rs[0] = context.getSheetDataList().size();
        rs[1] = rs[0] - context.getSheetDataFailureCount();
        rs[2] = context.getSheetDataFailureCount();
        return rs;
    }

    private void incrementFailureCount(SheetDataCheckResult sheetDataCheckResult) {
        sheetDataCheckResult.setSuccess(false);
        Integer bizFailureCount = sheetDataCheckResult.getBizFailureCount();
        sheetDataCheckResult.setBizFailureCount(++bizFailureCount);
    }


    /**
     * sheetName解析数据结果
     */
    @Data
    protected static class SheetParseResult<T extends RowErrorModel> {
        private List<T> bizDataList;
        private boolean success;
        private String sheetName;
        private List<String> lastHeadList;
    }

    /**
     * 业务数据校验阶段结果
     */
    @Data
    protected static class SheetDataCheckResult {
        private Boolean success = true;
        private Integer bizFailureCount = 0;
    }

    /**
     * excel生成阶段重写业务数据结果
     */
    @Data
    protected static class SheetDataRewriteConfig<T extends RowErrorModel> {
        private Integer sheetNum;
        private String sheetName;
        private Integer lastHeadNum;
        private List<String> dyncHeadList;
        private Boolean needReWriteDyncHead = false;
        private Class<? extends RowErrorModel> clazz;
        private List<T> bizDataList = Lists.newArrayList();
        private Map<Integer, Integer> columnWidthMap = Maps.newHashMap();
    }

    /**
     * excel解析监听器，主要用于sax解析场景
     *
     * @param <T> BaseRowModel 的子类
     */
    protected static class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

        private List<T> data = new ArrayList<T>();

        private boolean isParseSucceed;

        private String sheetName;

        private String innerLogTemplate;

        private List<String> lastHeadList;

        private Logger logger;

        public ExcelListener(Logger logger, String innerLogTemplate) {
            this.logger = logger;
            this.innerLogTemplate = innerLogTemplate;
        }

        /**
         * 每解析一行数据处理
         *
         * @param object  当前行数据javabean
         * @param context 解析上下文
         */
        @Override
        public void invoke(T object, AnalysisContext context) {
            String sheetName = context.getCurrentSheet().getSheetName();
            this.logger.info(innerLogTemplate, String.format("\n 当前sheet:%s, 已解析数据data:%s", sheetName, object));
            data.add(object);
        }

        /**
         * 当前sheet全部数据解析完成后处理
         *
         * @param context 解析上下文
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            isParseSucceed = context.getParseSuccess(); // excel row -> T 转换过程中是否存在数据异常
            sheetName = context.getCurrentSheet().getSheetName();
            lastHeadList = context.getCurrentSheet().getLastHeadList();
            this.logger.info(innerLogTemplate, String.format("当前sheet:%s, 解析完成，结果:%s", sheetName, isParseSucceed));
        }

        public List<T> getData() {
            return data;
        }

        public boolean isParseSucceed() {
            return isParseSucceed;
        }

        public String getSheetName() {
            return sheetName;
        }

        public List<String> getLastHeadList() {
            return lastHeadList;
        }
    }
}
