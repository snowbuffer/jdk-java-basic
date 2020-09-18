package com.snowbuffer.study.java.springmvc.annotation.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 01:18
 */
@Data
public class User2 {

    private String userName;

    private Integer age;

    private String remark;

    public User2(String userName, Integer age, String remark) {
        this.userName = userName;
        this.age = age;
        this.remark = remark;
    }
}
