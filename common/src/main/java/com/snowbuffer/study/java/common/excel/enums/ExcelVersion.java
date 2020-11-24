package com.snowbuffer.study.java.common.excel.enums;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-19 16:17
 */
public enum ExcelVersion {

    EXCEL_XLSX(1, "xlsx", "2007版本excel"),
    EXCEL_CSV(2, "csv", "csv版本excel"),
    EXCEL_XLS(3, "xls", "2003版本excel"),
    UNKNOWN(4, "?", "未知类型");

    private Integer code;
    private String suffix;
    private String desc;

    ExcelVersion(Integer code, String suffix, String desc) {
        this.code = code;
        this.suffix = suffix;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getSuffix() {
        return suffix;
    }

    public static ExcelVersion getByFilePath(String filePath) {
        for (ExcelVersion item : ExcelVersion.values()) {
            if (filePath.endsWith(item.getSuffix())) {
                return item;
            }
        }
        return UNKNOWN;
    }

}
