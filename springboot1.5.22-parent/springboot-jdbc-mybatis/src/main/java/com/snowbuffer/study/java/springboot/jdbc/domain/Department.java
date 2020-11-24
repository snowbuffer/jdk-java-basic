package com.snowbuffer.study.java.springboot.jdbc.domain;

/**
 * Description: 部门
 *
 * @author cjb
 * @since 2020-09-22 21:58
 */
public class Department {

    private Integer id;

    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
