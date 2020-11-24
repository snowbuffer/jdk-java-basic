package com.snowbuffer.study.java.common.excel;


import com.google.common.collect.Lists;
import com.snowbuffer.study.java.common.excel.enums.ImportSource;
import com.snowbuffer.study.java.common.excel.in.ImportConfig;
import com.snowbuffer.study.java.common.excel.in.ImportEngine;
import com.snowbuffer.study.java.common.excel.in.ImportHandler;
import com.snowbuffer.study.java.common.excel.in.demo.DemoStudentInfoImportHandler;

import java.util.List;


/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-14 00:32
 */
public class ImportEngineTest {

    public static void main(String[] args) {

        // 搜集handler
        List<ImportHandler<?>> importHandlers = Lists.newArrayList();
        DemoStudentInfoImportHandler handler = new DemoStudentInfoImportHandler();
        importHandlers.add(handler);

        // 实例化引擎
        ImportEngine importEngine = new ImportEngine(importHandlers);

        // 用户导入配置
        ImportConfig importConfig = new ImportConfig(
                "学生导入.xlsx", 1, "/Users/snowbuffer/Downloads/Dubbo/学生批量导入.xlsx", 10086L, ImportSource.STUDENT_IMPORT);
        importConfig.setDebug(true);

        // 开始导入
        importEngine.start(importConfig);

        // 关闭引擎
        importEngine.close();
    }
}