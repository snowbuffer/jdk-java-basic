package com.snowbuffer.study.java.common.excel.style;

import com.alibaba.excel.metadata.CustomCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class WrapTextColumCellStyle implements CustomCellStyle {

    @Override
    public CellStyle getStyle(Workbook workbook) {
        CellStyle newCellStyle = getBaseStyle(workbook);
        if (newCellStyle == null) {
            newCellStyle = workbook.createCellStyle();
        }
        // 自定义样式
//            Font font = workbook.createFont();
//            font.setColor(Font.COLOR_RED);
//            newCellStyle.setFont(font);

        newCellStyle.setWrapText(true);
        return newCellStyle;
    }

    @Override
    public CellStyle getBaseStyle(Workbook workbook) {
        // 基础样式
        CellStyle newCellStyle = workbook.createCellStyle();
        return newCellStyle;
    }

}