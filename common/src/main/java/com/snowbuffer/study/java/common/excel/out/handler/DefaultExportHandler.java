package com.snowbuffer.study.java.common.excel.out.handler;

import com.snowbuffer.study.java.common.excel.enums.ExportSource;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 22:52
 */
public class DefaultExportHandler extends AbstractExportHandler {
    @Override
    public boolean support(ExportSource module) {
        return true;
    }
}
