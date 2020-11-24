package com.snowbuffer.study.java.springboot.jdbc.controller;

import com.snowbuffer.study.java.springboot.jdbc.domain.Department;
import com.snowbuffer.study.java.springboot.jdbc.domain.Employee;
import com.snowbuffer.study.java.springboot.jdbc.mapper.DepartmentMapper;
import com.snowbuffer.study.java.springboot.jdbc.mapper.EmployeeMapper;
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
public class EmployeeController {

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping(value = "emp/{id}")
    public Employee getDepartment(@PathVariable(value = "id") Integer id) {
        return employeeMapper.getEmployeeById(id);
    }

    @GetMapping(value = "add/emp")
    public Employee addDepartment(Employee employee) {
        employeeMapper.insertEmployee(employee);

        return employee;
    }

    @GetMapping(value = "update/emp")
    public Employee updateDepartment(Employee employee) {
        employeeMapper.updateEmployee(employee);
        return employee;
    }

    @GetMapping(value = "delete/emp/{id}")
    public boolean deleteDepartment(@PathVariable(value = "id") Integer id) {
        employeeMapper.deleteEmployeeById(id);
        return true;
    }
}
