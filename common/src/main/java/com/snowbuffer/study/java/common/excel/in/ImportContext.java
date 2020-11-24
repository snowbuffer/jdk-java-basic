package com.snowbuffer.study.java.common.excel.in;

import com.alibaba.excel.metadata.RowErrorModel;
import com.snowbuffer.study.java.common.excel.enums.ExcelVersion;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Description: 导入上下文
 *
 * @author cjb
 * @version V1.0
 * @since 2020-11-13 13:29
 */
@Data
@ToString(callSuper = true)
@Slf4j
public class ImportContext<T extends RowErrorModel> {

    /**
     * 导入配置
     */
    private ImportConfig importConfig;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * excel版本
     */
    private ExcelVersion excelVersion;

    /**
     * 解析的sheetName
     */
    private String sheetName;

    /**
     * sheet数据列表
     */
    private List<T> sheetDataList;

    /**
     * sheet数据存在问题数
     */
    private Integer sheetDataFailureCount = 0;

    /**
     * 最后一个标题列名列表
     */
    private List<String> sheetLastHeadList;

    /**
     * 标题行， 从1开始
     */
    private Integer sheetLastTitleRownum;

    /**
     * 预制模板地址(主要用于输出文件样板)
     */
    private String prepareExcelTemplateAddress;

    /**
     * sheet页码
     */
    private Integer sheetNum;

    /**
     * sheetClass
     */
    private Class<? extends RowErrorModel> sheetClass;

    /**
     * 阶段性成功标记
     */
    private boolean phaseSuccess;

    // 日志
    private String message;

    // 日志 {}
    private String messageWithPlaceHodler1;

    // 日志 %s
    private String messageWithPlaceHodler2;


}

