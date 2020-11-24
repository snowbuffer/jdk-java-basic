package com.snowbuffer.study.java.common.excel.out;


import com.snowbuffer.study.java.common.excel.enums.ExportSource;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-27 17:29
 */
public interface ExportHandler {

    String DEFAULT_MESSAGE_TEMPLATE = "userId=%s, taskId=%s";

    String DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_1 = DEFAULT_MESSAGE_TEMPLATE + ", {}";

    String DEFAULT_MESSAGE_PLACEHODLER_TEMPLATE_2 = DEFAULT_MESSAGE_TEMPLATE + ", ";

    /**
     * 支持的模块
     *
     * @param module 模块类型
     * @return 是否支持
     */
    boolean support(ExportSource module);

    /**
     * 初始化上下文环境
     *
     * @param taskId       任务id
     * @param exportConfig 导出配置
     * @return 解析上下文
     */
    ExportContext init(Long taskId, ExportConfig exportConfig);

    /**
     * 搜集业务数据
     *
     * @param context
     */
    void collectExportData(ExportContext context);

    /**
     * 业务数据搜集失败处理
     *
     * @param context
     */
    void failureByCollectExportData(ExportContext context);

    /**
     * 开始导出业务数据
     *
     * @param context
     */
    void exportData(ExportContext context);

    /**
     * 导出业务数据失败处理
     *
     * @param context
     */
    void failureByExportData(ExportContext context);

    /**
     * 导出成功处理
     *
     * @param context
     */
    void exportDataSuccess(ExportContext context);

    /**
     * 阶段失败场景
     *
     * @param context   解析上下文
     * @param exception 异常实例
     * @param stepPhase 处理阶段
     */
    void failureBySetpPhaseExcpetion(
            ExportContext context, Exception exception, ExportEngine.StepProvider.StepPhase stepPhase);

    /**
     * 兜底异常[兜底异常]
     *
     * @param context   解析上下文
     * @param exception 异常实例
     */
    void failureByGlobalExcpetion(
            ExportContext context, Exception exception);

    /**
     * 关闭业务容器
     *
     * @param context
     */
    void close(ExportContext context);

}
