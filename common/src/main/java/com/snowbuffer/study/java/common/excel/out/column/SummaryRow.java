package com.snowbuffer.study.java.common.excel.out.column;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-09-20 11:28
 */
@Slf4j
public class SummaryRow {

    public static final String EMPTY_VALUE = "";

    private String sheetName;
    private Integer columnsSize;

    private Map<Integer, ExportColumn> mapping = new HashMap<>();

    public SummaryRow(String sheetName, Integer columnsSize) {
        this.sheetName = sheetName;
        this.columnsSize = columnsSize;
    }

    public void put(int i, ExportColumn column) {
        mapping.put(i, column);
    }

    public List<Object> newSummaryRow(List<?> bizDataList) {
        List<Object> list = Lists.newArrayList();
        for (int i = 0; i < columnsSize; i++) {
            list.add(EMPTY_VALUE);
        }
        if (log.isDebugEnabled()) {
            log.debug("sheetName => {} 初始化汇总行 => {}", sheetName, list);
        }
        if (CollectionUtils.isEmpty(bizDataList)) {
            log.warn("sheetName => {} 没有对应的业务数据，不需要进行汇总", sheetName);
            return null;
        }
        mapping.forEach((index, column) -> {
            try {
                list.set(index, column.getSummaryAction().apply(bizDataList));
            } catch (Exception e) {
                log.error(String.format("sheetName => {}, header => {} 汇总数据发生未知异常，跳过此列数据汇总", sheetName, column.getHeader()), e);
            }
        });
        if (log.isDebugEnabled()) {
            log.debug("sheetName => {} 完成汇总行数据汇总 => {}", sheetName, list);
        }
        return list;
    }
}

