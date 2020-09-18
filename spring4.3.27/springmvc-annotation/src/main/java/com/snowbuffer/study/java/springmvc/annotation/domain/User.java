package com.snowbuffer.study.java.springmvc.annotation.domain;

import com.snowbuffer.study.java.springmvc.annotation.controller.CommonTestController;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 01:18
 */
@Data
public class User {

    private String userName;

    private Integer age;

    private String remark;

    private Date registerDate;

    public User() {
    }

    public User(String userName, Integer age, String remark) {
        this.userName = userName;
        this.age = age;
        this.remark = remark;
    }

}
