package com.snowbuffer.study.java.common.excel.in.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.RowErrorModel;
import com.snowbuffer.study.java.common.excel.style.WrapTextColumCellStyle;
import lombok.Data;

@Data
public class DemoStudentInfoImportVO extends RowErrorModel {

    /**
     * 学名姓名
     */
    @ExcelProperty(index = 0, value = {"学生姓名"})
    private String personName;

    private Long platformSourceId;

    private Long companyId;

    private Long schoolId;

    @ExcelProperty(index = 1, value = {"登录帐号"})
    private String userName;

    @ExcelProperty(index = 2, value = {"密码"}, commonCellStyle = WrapTextColumCellStyle.class, errorCellStyle = WrapTextColumCellStyle.class)
    private String password;
    /**
     * 所在学校
     */
    @ExcelProperty(index = 3, value = {"所在学校"})
    private String schoolName;

    /**
     * 学籍号
     */
    @ExcelProperty(index = 4, value = {"学籍号"})
    private String schoolNumber;

    private Long studySectionId;
    /**
     * 年级
     */
    @ExcelProperty(index = 5, value = {"年级"})
    private String studySectionName;

    private Long classId;
    /**
     * 班级
     */
    @ExcelProperty(index = 6, value = {"班级"})
    private String className;

    /**
     * 家长姓名
     */
    @ExcelProperty(index = 7, value = {"家长姓名"})
    private String familyPersonName;

    /**
     * 家长姓名
     */
    @ExcelProperty(index = 8, value = {"家长联系方式"})
    private String familyPersonMobile;

}