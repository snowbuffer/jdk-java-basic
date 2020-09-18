package com.snowbuffer.study.java.common.jackson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 1、@JsonFormat：指定日期属性序列化与反序列化时的格式，timezone = "GMT+8" 设置时区，表示 +8 小时，否则会少8小时
 * 2、@JsonFormat 添加到需要指定格式的日期属性上
 * 3、JsonInclude(JsonInclude.Include.NON_NULL)：对值为 null 的属性不进行序列化
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Book {

    private Integer id;
    private String title;
    private Float price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 如果格式化，就一定要加上时区， 默认是GMT+0时区
    private Date publish; // 默认输出的是时间戳， 输出时间戳戳时候，时区默认以当前平台时间为准

    public Book() {
    }

    public Book(Integer id, String title, Float price, Date publish) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.publish = publish;
    }
}