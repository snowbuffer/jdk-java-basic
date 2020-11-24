package com.snowbuffer.study.java.common.excel.enums;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 11:19
 */
public enum ExportSource {
    ;

    private Integer code;
    private String desc;

    ExportSource(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ExportSource getByCode(Integer code) {
        for (ExportSource item : ExportSource.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }
}
