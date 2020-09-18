package com.snowbuffer.study.java.spring.annotation.tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 20:02
 */
public class UserService {

    @Autowired
    public JdbcTemplate jdbcTemplate;


    @Transactional(rollbackFor = {IOException.class})
    public void update(String id, String content) {

        int update = this.jdbcTemplate.update(
                "update book_class set bookclass = ? where bookclassno = ?", content, id);
        System.out.println("操作：" + (update > 0));

        if (content == null) {
            throw new RuntimeException("更新失败,需要进行回滚: content:" + content);
        }

    }

    public void query(String id) {
        String name = this.jdbcTemplate.queryForObject(
                "select bookclass from book_class where bookclassno = ?",
                new Object[]{id},
                String.class);
        System.out.println("id:" + id + " => " + name);
    }
}
