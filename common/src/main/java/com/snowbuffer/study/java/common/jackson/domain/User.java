package com.snowbuffer.study.java.common.jackson.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer uId;
    private String uName;
    private Date birthday;
    private Float price;

    public User() {
    }

    public User(Integer uId, String uName, Date birthday, Float price) {
        this.uId = uId;
        this.uName = uName;
        this.birthday = birthday;
        this.price = price;
    }

}