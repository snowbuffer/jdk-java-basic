package com.snowbuffer.study.java.common.excel.out;

import com.snowbuffer.study.java.common.excel.enums.ExportSource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 11:17
 */
@Setter
@Getter
public class ExportConfig {


    private boolean debug;

    /**
     * 用户id 必填项
     */
    private Long userId;

    /**
     * 下载文件名称
     */
    private String downLoadFileName;

    /**
     * 归属模块
     */
    private ExportSource exportSource;

    /**
     * sheetWorkers
     */
    private List<SheetWorker<?>> sheetWorkers;


}
