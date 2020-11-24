package com.snowbuffer.study.java.springboot.jdbc.mapper;

import com.snowbuffer.study.java.springboot.jdbc.domain.Employee;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-09-22 22:01
 */
public interface EmployeeMapper {

    Employee getEmployeeById(Integer id);

    int deleteEmployeeById(Integer id);

    int insertEmployee(Employee employee);

    int updateEmployee(Employee employee);
}
