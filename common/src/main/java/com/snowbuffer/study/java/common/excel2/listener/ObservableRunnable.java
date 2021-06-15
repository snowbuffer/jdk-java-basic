package com.snowbuffer.study.java.common.excel2.listener;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 14:09
 */
public abstract class ObservableRunnable<T> implements Runnable {

    private LifeListener<T> listener;

    public ObservableRunnable(final LifeListener<T> listener) {
        this.listener = listener;
    }

    protected void notifyChange(final RunnableEvent<T> event) {
        this.listener.onEvent(event);
    }

    public enum RunnableState {
        INIT(0, "任务初始化"),
        PARSING(5, "解析中"),
        PARSE_UNKNOWN_EXCEPTION(10, "解析失败-未知异常[流程结束]"),
        CHECKING(15, "检查数据中"),
        CHECK_UNKNOWN_EXCEPTION(20, "检查数据失败-未知异常[流程结束]"),
        IMPORTING(25, "导入中"),
        IMPORT_UNKNOWN_EXCEPTION(30, "导入失败-未知异常[流程结束]"),
        SUCCESS(35, "全部操作成功[流程结束]"),

        WRITING_EXCEL_DATA(40, "创建excel文件中"),
        WRITING_EXCEL_DATA_UNKNOWN_EXCEPTION(45, "创建excel文件失败-未知异常[流程结束]"),
        UPLOADING(50, "上传文件中"),
        UPLOADING_UNKNOWN_EXCEPTION(55, "上传文件失败-未知异常[流程结束]"),
        UPLOADING_SUCCESS(60, "上传文件成功[流程结束]"),

        GLOBAL_UNKNOWN_EXCEPTION(65, "全局未知异常[流程结束]"),
        ;

        private Integer code;

        private String msg;

        RunnableState(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    @Data
    @SuperBuilder
    public static class RunnableEvent<T> {
        private Long uniqueId;
        private RunnableState runnableState;
        private List<T> dataList;
        private String uploadUrl;
        private Throwable cause;

    }
}
