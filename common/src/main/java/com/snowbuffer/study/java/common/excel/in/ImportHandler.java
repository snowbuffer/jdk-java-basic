package com.snowbuffer.study.java.common.excel.in;

import com.alibaba.excel.metadata.RowErrorModel;
import com.snowbuffer.study.java.common.excel.enums.ImportSource;

/**
 * Description: 导入处理器
 *
 * @author cjb
 * @since 2020-11-11 19:55
 */
public interface ImportHandler<T extends RowErrorModel> {

    String DEFAULT_MESSAGE_TEMPLATE = "userId=%s, taskId=%s";

    String DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_1 = DEFAULT_MESSAGE_TEMPLATE + ", {}";

    String DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_2 = DEFAULT_MESSAGE_TEMPLATE + ", ";

    /**
     * 支持的模块
     *
     * @param module 模块类型
     * @return 是否支持
     */
    boolean support(ImportSource module);

    /**
     * 初始化上下文环境
     *
     * @param taskId       任务id
     * @param importConfig 导入配置
     * @return 解析上下文
     */
    ImportContext<T> init(Long taskId, ImportConfig importConfig);

    /**
     * 读取excel  主要是解析excel数据到javaBean
     *
     * @param context 解析上下文
     */
    void read(ImportContext<T> context);

    /**
     * read读取不成功处理
     *
     * @param context 解析上下文
     */
    void failureByRead(ImportContext<T> context);

    /**
     * 导入前检查数据处理
     *
     * @param context 解析上下文
     */
    void checkDataBeforeImport(ImportContext<T> context);

    /**
     * 导入前检查数据处理[失败场景]
     *
     * @param context 解析上下文
     */
    void failureByCheckDataBeforeImport(ImportContext<T> context);

    /**
     * 数据导入
     *
     * @param context 解析上下文
     */
    void importData(ImportContext<T> context);

    /**
     * 数据导入[失败场景]
     *
     * @param context 解析上下文
     */
    void failureByImportData(ImportContext<T> context);

    /**
     * 数据导入成功
     *
     * @param context 解析上下文
     */
    void importDataSuccess(ImportContext<T> context);

    /**
     * 阶段失败场景
     *
     * @param context   解析上下文
     * @param exception 异常实例
     * @param stepPhase 处理阶段
     */
    void failureBySetpPhaseExcpetion(
            ImportContext<T> context, Exception exception, ImportEngine.StepProvider.StepPhase stepPhase);

    /**
     * 兜底异常[兜底异常]
     *
     * @param context   解析上下文
     * @param exception 异常实例
     */
    void failureByGlobalExcpetion(
            ImportContext<T> context, Exception exception);

    /**
     * 释放资源(删除临时文件，关闭流等)
     *
     * @param context 解析上下文
     */
    void close(ImportContext<T> context);

}
