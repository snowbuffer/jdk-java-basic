package com.snowbuffer.study.java.common.excel.enums;

import com.alibaba.excel.metadata.RowErrorModel;
import com.snowbuffer.study.java.common.excel.in.demo.DemoStudentInfoImportVO;
import lombok.Data;
import lombok.Getter;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-28 11:07
 */
@Getter
public enum ImportSource {

    STUDENT_IMPORT("学员批量导入", 1, new PrepareTemplate("templates/import_student", 2, DemoStudentInfoImportVO.class)),
    ;

    // 编码
    private Integer code;

    // EXCEL模板文件
    private PrepareTemplate prepareTemplate;

    // 描述
    private String desc;

    ImportSource(String desc, Integer code, PrepareTemplate prepareTemplate) {
        this.desc = desc;
        this.code = code;
        this.prepareTemplate = prepareTemplate;
    }

    public PrepareTemplate getPrepareTemplate() {
        return prepareTemplate;
    }

    @Data
    public static class PrepareTemplate {
        private String address;

        /**
         * excle:
         * 0:学生姓名（必填）	账号名（必填）	密码（必填）	所在学校（必填）	学籍号	年级（必填）	班级（必填）	家长姓名	家长联系方式
         * 1:Marry	student5	yPO51X^g	Hangzhou school		K	1	Jack	13866666667
         * 这里的dataFirstRownum指的就是：1
         */
        private int dataFirstRownum; /*业务数据的第一行的物理行号， 从1开始计数*/

        private Class<? extends RowErrorModel> sheetClass; /**/

        public PrepareTemplate(String address, int dataFirstRownum, Class<? extends RowErrorModel> sheetClass) {
            this.address = address;
            this.dataFirstRownum = dataFirstRownum;
            this.sheetClass = sheetClass;
        }
    }
}
