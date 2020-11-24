package com.snowbuffer.study.java.common.excel.out;

import com.google.common.collect.Maps;
import com.snowbuffer.study.java.common.excel.enums.ExcelVersion;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 11:16
 */
@Setter
@Getter
public class ExportContext {

    /**
     * 导出配置
     */
    private ExportConfig exportConfig;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * excel版本
     */
    private ExcelVersion excelVersion;

    // 业务数据容器 javaBean集合
    private Map<SheetWorker<?>, LinkedList<Object>> bizDataMap
            = Maps.newTreeMap(Comparator.comparingInt(SheetWorker::getSheetIndex));

    /**
     * 阶段性成功标记
     */
    private boolean phaseSuccess;

    /**
     * 导出文件存储地址
     */
    private String storeUrl;

    // 日志
    private String message;

    // 日志 {}
    private String messageWithPlaceHodler1;

    // 日志 %s
    private String messageWithPlaceHodler2;

}
