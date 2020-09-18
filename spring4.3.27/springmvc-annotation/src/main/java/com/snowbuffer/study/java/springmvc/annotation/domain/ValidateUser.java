package com.snowbuffer.study.java.springmvc.annotation.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Date;
import java.util.Locale;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 01:18
 */
@Data
@Validated
public class ValidateUser {

    @Valid
    @Email(message = "不是一个邮箱")
    private String remark;


    @NotEmpty(message = "不能为null")
    private String userName;

    private Integer age;


    private Date registerDate;

    public ValidateUser() {
    }

    public ValidateUser(String userName, Integer age, String remark) {
        this.userName = userName;
        this.age = age;
        this.remark = remark;
    }

    public static void main(String[] args) {
        Locale aDefault = Locale.getDefault();
        System.out.println(aDefault);
    }

}
