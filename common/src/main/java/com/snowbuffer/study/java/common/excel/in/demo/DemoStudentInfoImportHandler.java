package com.snowbuffer.study.java.common.excel.in.demo;

import com.google.gson.Gson;
import com.snowbuffer.study.java.common.excel.enums.ImportSource;
import com.snowbuffer.study.java.common.excel.in.handler.AbstractImportHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-13 23:02
 */
@Slf4j
@Service
public class DemoStudentInfoImportHandler extends AbstractImportHandler<DemoStudentInfoImportVO> {


    @Override
    protected boolean checkRecord(
            Map<String, Object> bizParamMap, DemoStudentInfoImportVO recordVO,
            List<DemoStudentInfoImportVO> sheetDataList, Map<Object, DemoStudentInfoImportVO> visitedMap) {
        System.out.println(new Gson().toJson(recordVO));
        recordVO.addBizError("数据校验不正确");
        return false;
    }

    @Override
    protected boolean importRecord(
            Map<String, Object> bizParamMap, DemoStudentInfoImportVO recordVO,
            List<DemoStudentInfoImportVO> sheetDataList, Map<Object, DemoStudentInfoImportVO> visitedMap) {
        return false;
    }

    @Override
    public boolean support(ImportSource module) {
        return module.getPrepareTemplate().getSheetClass() == DemoStudentInfoImportVO.class;
    }

    /**
     * 测试代码
     *//*
    public static void main(String[] args) {

        // 搜集handler
        List<ImportHandler<?>> importHandlers = Lists.newArrayList();
        DemoStudentInfoImportHandler handler = new DemoStudentInfoImportHandler();
        importHandlers.add(handler);

        // 实例化引擎
        ImportEngine importEngine = new ImportEngine(importHandlers);

        // 用户导入配置
        ImportConfig importConfig = new ImportConfig(
                "学生导入.xlsx", "/Users/snowbuffer/Downloads/Dubbo/学生批量导入.xlsx", 10086L, ImportSource.STUDENT_IMPORT);
        importConfig.setDebug(true);

        // 开始导入
        importEngine.start(importConfig);

        // 关闭引擎
        importEngine.close();
    }*/
}
