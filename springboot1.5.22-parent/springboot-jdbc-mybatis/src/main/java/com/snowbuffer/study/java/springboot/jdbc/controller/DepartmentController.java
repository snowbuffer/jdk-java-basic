package com.snowbuffer.study.java.springboot.jdbc.controller;

import com.snowbuffer.study.java.springboot.jdbc.domain.Department;
import com.snowbuffer.study.java.springboot.jdbc.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-09-22 22:06
 */
@RestController
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;

    @GetMapping(value = "dept/{id}")
    public Department getDepartment(@PathVariable(value = "id") Integer id) {
        return departmentMapper.getDepartById(id);
    }

    @GetMapping(value = "add/dept")
    public Department addDepartment(Department department) {
        departmentMapper.insertDepart(department);

        return department;
    }

    @GetMapping(value = "update/dept")
    public Department updateDepartment(Department department) {
        departmentMapper.updateDept(department);
        return department;
    }

    @GetMapping(value = "delete/dept/{id}")
    public boolean deleteDepartment(@PathVariable(value = "id") Integer id) {
        departmentMapper.deleteDeptById(id);
        return true;
    }
}
