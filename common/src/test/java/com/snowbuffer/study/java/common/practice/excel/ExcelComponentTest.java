package com.snowbuffer.study.java.common.practice.excel;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-29 21:03
 */
public class ExcelComponentTest {

    public static void main(String[] args) {

        String[] requests = {
                "aa|30|aa|123456",
                "bb|30|bb|123456",
                "cc|30|cc|123456",
                "dd|30|dd|123456",
                "ee|30|ee|123456"
        };
        generate(requests, "测试", "/Users/xx/Documents/dev/workbook.xlsx");
    }

    public static void generate(String[] dataList, String sheetName, String location) {

        ExcelComponent excelComponent = new ExcelComponent();

        // 初始化excel上下文
        excelComponent.initExcelContext();

        // touch新的workbook
        excelComponent.touchWorkbook();

        // touch新的sheet
        excelComponent.touchSheet(sheetName);

        // 设置sheet单元格总列数
        excelComponent.setSheetTotalCellNum(6);

        // 设置sheet单元格列宽
        excelComponent.setSheetCellColumnWith(10);

        // 创建第一行
        excelComponent.createRow(new String[]{"", "", "", "", "", ""});

        // 创建第二行
        excelComponent.createRow(new String[]{"", "a", "b", "c", "d", ""});

        // 创建数据项
        for (int i = 0; i < dataList.length; i++) {
            String data = dataList[i];
            data = "-|" + data + "|-";
            String[] requestParams = data.split("\\|");
            excelComponent.createRow(requestParams);
        }

        // 打开位置
        excelComponent.openLocation(location);

        // 生成excel
        excelComponent.generateExcel();

        // 释放资源
        excelComponent.close();

    }
}