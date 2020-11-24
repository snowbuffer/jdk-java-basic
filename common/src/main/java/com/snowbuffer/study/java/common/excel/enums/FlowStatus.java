package com.snowbuffer.study.java.common.excel.enums;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-18 17:36
 */
public enum FlowStatus {

    // 导出
    EXPORT_WAITING_QUEUE(-2, "任务已进入队列"),
    EXPORTING(-1, "导出中"),
    EXPORT_SUCCESS(1, "导出成功"),
    EXPORT_FAILURE(0, "导出失败"),

    // 导入
    IMPORT_WAITING_QUEUE(1, "任务已进入队列"),
    IMPORT_PARSEING(2, "解析excel中"),
    IMPORT_PARSEING_FAILURE(3, "解析excel失败"),
    IMPORT_PARAM_CHECKING(4, "数据校验中"),
    IMPORT_PARAM_CHECKING_FAILURE(5, "数据校验失败"),
    IMPORT_DB_IMPORTING(6, "数据入库中"),
    IMPORT_SUCCESS_PART(7, "导入成功(部分落库)"),
    IMPORT_SUCCESS_FULL(8, "导入成功(全部落库)"),

    UNKNOW(-1, "");

    private Integer code;
    private String desc;

    FlowStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FlowStatus getByCode(Integer code) {
        for (FlowStatus item : FlowStatus.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }
}
