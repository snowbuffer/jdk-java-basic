package com.snowbuffer.study.java.common.excel2;

import com.google.common.collect.Maps;
import com.snowbuffer.study.java.common.excel2.annotation.ExcelColumn;
import com.snowbuffer.study.java.common.excel2.parser.ExcelParser;
import com.snowbuffer.study.java.common.excel2.parser.HutoolExcelParser;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 16:00
 */
public class ExcelConfigFactory {

    public static <T> ExcelParser<T> getDefaultParser(Class<T> clazz) {
        return new HutoolExcelParser<>(clazz); // 每次都要新实例
    }

    public static <T> Map<String, ExcelColumn> getExcelColumnConfig(Class<T> clazz) {
        Map<String, ExcelColumn> columnMap = Maps.newLinkedHashMap();
        ReflectionUtils.doWithFields(clazz, field -> {
            ExcelColumn annotation = AnnotationUtils.findAnnotation(field, ExcelColumn.class);
            columnMap.put(field.getName(), annotation);
        }, field -> AnnotationUtils.findAnnotation(field, ExcelColumn.class) != null);
        return columnMap;
    }
}
