package com.snowbuffer.study.java.common.excel2.parser;

import java.io.File;
import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 11:16
 */
public interface ExcelParser<T> {

    List<T> parse(String absolutePath);

    File write(List<T> dataList);
}
