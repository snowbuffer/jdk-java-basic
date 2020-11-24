package com.snowbuffer.study.java.common.excel.in.strategy;

import com.alibaba.excel.metadata.RowErrorModel;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-12 13:57
 */
public interface RowAccessorStrategy<T extends RowErrorModel> {

    /**
     * 消费每一行页数数据
     *
     * @param bizParamMap   用户导入请求信息Map
     * @param recordVO      当前行记录javaBean
     * @param sheetDataList 全量数据
     * @param visitedMap    已经遍历过的javaBean Map  每次遍历都会重置Map
     * @return 当前行记录javaBean 业务校验是否成功
     */
    boolean visit(
            Map<String, Object> bizParamMap, T recordVO,
            List<T> sheetDataList, Map<Object, T> visitedMap);
}
