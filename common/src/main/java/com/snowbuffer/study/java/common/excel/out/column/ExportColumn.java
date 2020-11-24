package com.snowbuffer.study.java.common.excel.out.column;

import lombok.Data;

import java.util.List;
import java.util.function.Function;

@Data
public class ExportColumn<T> {

    /**
     * 标题
     */
    String header;

    /**
     * 列宽
     */
    Integer columnWidth;

    /**
     * 填充单元格函数
     */
    Function<T, Object> action;

    /**
     * 计算汇总行单元格函数
     */
    Function<List<T>, Object> summaryAction;

    public ExportColumn(String header, Function<T, Object> action) {
        this(header, action, null, null);
    }

    public ExportColumn(String header, Function<T, Object> action, Integer columnWidth) {
        this(header, action, null, columnWidth);
    }

    public ExportColumn(String header, Function<T, Object> action, Function<List<T>, Object> summaryAction) {
        this(header, action, summaryAction, null);
    }

    public ExportColumn(String header, Function<T, Object> action, Function<List<T>, Object> summaryAction, Integer columnWidth) {
        this.header = header;
        this.action = action;
        this.summaryAction = summaryAction;
        this.columnWidth = columnWidth;
    }

    public boolean existSummaryAction() {
        return summaryAction != null;
    }

    public boolean existColumnWidth() {
        return columnWidth != null && (columnWidth > 0 && columnWidth < 255);
    }
}
