package com.snowbuffer.study.java.common.excel.in;

import com.google.common.collect.Maps;
import com.snowbuffer.study.java.common.excel.enums.ImportSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 导入配置
 */
@Setter
@Getter
public class ImportConfig {

    private boolean debug;

    /**
     * 用户上传文件名称 带后缀(该文件名在导入过程失败时候，会作为云存储路径的一部分)
     */
    private String fileName;

    /**
     * 用户上传文件路径
     */
    private String filePath;

    /**
     * 用户id 必填项
     */
    private Long userId;

    /**
     * sheetNum
     */
    private Integer sheetNum;

    /**
     * 归属模块
     */
    private ImportSource importSource;

    /**
     * 业务参数Map
     */
    private Map<String, Object> bizParamMap;

    public ImportConfig(String fileName, Integer sheetNum, String filePath, Long userId, ImportSource importSource) {
        this(fileName, sheetNum, filePath, userId, importSource, null);
    }

    public ImportConfig(String fileName, Integer sheetNum, String filePath, Long userId, ImportSource importSource, Map<String, Object> bizParamMap) {
        Assert.isTrue(StringUtils.isNotBlank(fileName), "fileName不能为空");
        Assert.isTrue(StringUtils.isNotBlank(filePath), "filePath不能为空");
        Assert.isTrue(userId != null, "userId不能为空");
        Assert.isTrue(importSource != null, "importSource不能为空");
        Assert.isTrue(sheetNum != null && sheetNum > 0, "userId不能为空");
        this.fileName = fileName;
        this.filePath = filePath;
        this.sheetNum = sheetNum;
        this.userId = userId;
        this.importSource = importSource;
        this.bizParamMap = bizParamMap;
        if (this.bizParamMap == null) {
            this.bizParamMap = Maps.newHashMap();
        }
    }
}
