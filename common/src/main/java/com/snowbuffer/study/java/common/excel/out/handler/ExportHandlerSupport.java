package com.snowbuffer.study.java.common.excel.out.handler;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.snowbuffer.study.java.common.excel.out.ExportContext;
import com.snowbuffer.study.java.common.excel.out.SheetWorker;
import com.snowbuffer.study.java.common.excel.out.column.ExportColumn;
import com.snowbuffer.study.java.common.excel.out.merge.Point;
import com.snowbuffer.study.java.common.excel.support.AbstractHandlerSupport;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 15:30
 */
@Slf4j
public abstract class ExportHandlerSupport extends AbstractHandlerSupport {

    /**
     * 生成csv
     */
    protected void genCsv(ExportContext context) {
        boolean debug = context.getExportConfig().isDebug();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();
        String message = context.getMessage();

        // 生成csv文件
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVWriter csvWriter = null;
        try {
            SheetWorker worker = context.getBizDataMap().keySet().iterator().next();
            LinkedList bizDataList = context.getBizDataMap().values().iterator().next();
            // 原有gbk只能在windows平台使用，mac乱码，因此统一调整为utf-8 + bom 格式，但部分windows会出现乱码
            out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            OutputStreamWriter fwriter = new OutputStreamWriter(out, "UTF-8");
            csvWriter = new CSVWriter(fwriter);

            // 写表头
            List<ExportColumn> columns = worker.getExportColumns();
            String[] headArray = new String[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                headArray[i] = columns.get(i).getHeader();
            }
            if (debug) {
                log.info(message, String.format("开始写入标题内容:%s", Arrays.toString(headArray)));
            }
            csvWriter.writeNext(headArray);

            if (debug) {
                log.info(message, "开始写入内容");
            }
            // 写数据内容
            for (Object data : bizDataList) {
                String[] rowData = new String[columns.size()];
                for (int i = 0; i < columns.size(); i++) {
                    Object value = columns.get(i).getAction().apply(data);
                    rowData[i] = value == null ? null : value.toString();
                }
                csvWriter.writeNext(rowData);
            }
            csvWriter.flush();

            if (debug) {
                log.info(message, "完成所有数据写入,开始上传文件...");
            }
        } catch (Exception e) {
            context.setPhaseSuccess(false);
            log.error(String.format(messageWithPlaceHodler2, "生成CSV文件发生未知异常"), e);
        }

        // 上传csv文件
        try {
            if (context.isPhaseSuccess()) {
                // 上传
                String url = upload(
                        context.getMessageWithPlaceHodler1(),
                        context.getExportConfig().getDownLoadFileName(),
                        out.toByteArray());
                context.setStoreUrl(url);

                // 清空业务数据 减少时间占用
                context.setBizDataMap(null);
            }
        } catch (Exception e) {
            context.setPhaseSuccess(false);
            log.error(String.format(messageWithPlaceHodler2, "上传CSV文件发生未知异常"), e);
        } finally {
            closeAllStream(context.getMessageWithPlaceHodler2(), out, csvWriter);
        }

    }

    /**
     * 生成excel
     */
    protected void genExcel(ExportContext context, ExcelTypeEnum excelTypeEnum, ByteArrayOutputStream out) {

        boolean debug = context.getExportConfig().isDebug();
        String messageWithPlaceHodler1 = context.getMessageWithPlaceHodler1();
        String messageWithPlaceHodler2 = context.getMessageWithPlaceHodler2();
        String message = context.getMessage();

        ExcelWriter writer = new ExcelWriter(out, excelTypeEnum, true);
        try {
            int sheetIndex = 0;
            for (Map.Entry<SheetWorker<?>, LinkedList<Object>> entry : context.getBizDataMap().entrySet()) {
                sheetIndex++;
                com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(sheetIndex, 1); // settings限制，title行数只能为1

                SheetWorker worker = entry.getKey();
                LinkedList<?> bizDataList = entry.getValue();

                if (worker.existPointManager()) {
                    if (debug) {
                        log.info(messageWithPlaceHodler1, String.format("发现存在多级表头：sheetName:%s",
                                sheet.getSheetName()));
                    }
                    // 多级标题头
                    sheet.setTitleList(worker.getPointManager().getTitleList());
                }

                List<ExportColumn> columns = worker.getExportColumns();
                List<List<String>> headList = Lists.newArrayList();
                for (int i = 0; i < columns.size(); i++) {
                    List<String> temp = Lists.newArrayList();
                    headList.add(temp);
                    temp.add(columns.get(i).getHeader());
                }
                sheet.setSheetName(worker.getSheetName());
                sheet.setHead(headList);
                sheet.setStartRow(0);

                // 写数据内容
                List<List<Object>> allDataList = Lists.newArrayList();
                for (Object data : bizDataList) {
                    List<Object> rowDataList = Lists.newArrayList();
                    for (int i = 0; i < columns.size(); i++) {
                        Object value = columns.get(i).getAction().apply(data);
                        rowDataList.add(value);
                    }
                    allDataList.add(rowDataList);
                }

                if (debug) {
                    log.info(messageWithPlaceHodler1, String.format("已搜集业务数据：sheetName:%s, size:%s",
                            sheet.getSheetName(), allDataList.size())
                    );
                }

                // 汇总行
                if (worker.existSummaryRow()) {
                    List<Object> summaryList = worker.getSummaryRow().newSummaryRow(bizDataList);
                    if (false == CollectionUtils.isEmpty(summaryList)) {
                        if (debug) {
                            log.info(messageWithPlaceHodler1, String.format("发现存在汇总行：sheetName:%s",
                                    sheet.getSheetName()));
                        }
                        allDataList.add(summaryList);
                    }
                }

                if (debug) {
                    log.info(messageWithPlaceHodler1, String.format("开始写入业务数据：sheetName:%s, 总大小:%s",
                            sheet.getSheetName(), allDataList.size()));
                }

                writer.write1(allDataList, sheet);

                // 合并单元格
                if (worker.existPointManager()) {
                    if (debug) {
                        log.info(messageWithPlaceHodler1, String.format("发现存在需要合并的单元格：sheetName:%s",
                                sheet.getSheetName()));
                    }
                    List<Point> pointList = worker.getPointManager().getPointList();
                    for (Point point : pointList) {
                        List<Integer> pointRangeList = point.getPointRangeList();
                        writer.merge(
                                pointRangeList.get(0),
                                pointRangeList.get(1),
                                pointRangeList.get(2),
                                pointRangeList.get(3));
                    }
                }

                // 设置列宽
                if (worker.existColumnWidthMap()) {
                    if (debug) {
                        log.info(messageWithPlaceHodler1, String.format("发现存在列宽：sheetName:%s", sheet.getSheetName()));
                    }
                    writer.setColumnWidth(worker.getColumnWidthMap(), 10);
                }
            }
        } catch (Exception e) {
            context.setPhaseSuccess(false);
            log.error(String.format(messageWithPlaceHodler2, "生成XLS|XLSX发生未知异常"), e);
        } finally {
            writer.finish();
        }
    }

}
