package com.snowbuffer.study.java.springboot.mvc.controller.management;

import com.snowbuffer.study.java.springboot.mvc.dao.DepartmentDao;
import com.snowbuffer.study.java.springboot.mvc.dao.EmployeeDao;
import com.snowbuffer.study.java.springboot.mvc.entities.Department;
import com.snowbuffer.study.java.springboot.mvc.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Description:
 * <p>
 * <table border>
 * <tr>
 * <tr><th colspan=3 align=center> &ensp;功能 &ensp;</th><th > &ensp;请求地址 &ensp;</th><th>&ensp;请求方式 &ensp;</th>
 * </tr>
 * <tr>
 * <td colspan=3>查询所有员工</td><td>&ensp;emps</td><td>&ensp;GET</td>
 * </tr>
 * <tr>
 * <td colspan=3>查询员工详情界面</td><td>&ensp;emp/{id}</td><td>&ensp;GET</td>
 * </tr>
 * <tr>
 * <td colspan=3>进入添加员工界面</td><td>&ensp;emp</td><td>&ensp;GET</td>
 * </tr>
 * <tr>
 * <td colspan=3>添加员工</td><td>&ensp;emp</td><td>&ensp;POST</td>
 * </tr>
 * <tr>
 * <td colspan=3>修改员工界面(回显)</td><td>&ensp;emp/{id}</td><td>&ensp;GET</td>
 * </tr>
 * <tr>
 * <td colspan=3>修改员工</td><td>&ensp;emp</td><td>&ensp;PUT</td>
 * </tr>
 * <tr>
 * <td colspan=3>删除员工</td><td>&ensp;emp/{id}</td><td>&ensp;DELETE</td>
 * </tr>
 * </table>
 *
 * @author cjb
 * @since 2020-09-21 22:48
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("emps")
    public String listEmps(Map<String, Object> returnMap) {
        Collection<Employee> list = employeeDao.getAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        returnMap.put("emps", list);
        return "/emp/list";
    }

    @GetMapping("emp/detail/{id}")
    public String getEmp(@PathVariable(value = "id") Integer id, Map<String, Object> returnMap) {

        Collection<Department> list = departmentDao.getDepartments();
        if (list == null) {
            list = new ArrayList<>();
        }
        returnMap.put("departments", list);

        Employee employee = employeeDao.get(id);
        returnMap.put("emp", employee);

        return "/emp/detail";
    }

    @GetMapping("emp")
    public String addEmpUI(Map<String, Object> returnMap) {

        Collection<Department> list = departmentDao.getDepartments();
        if (list == null) {
            list = new ArrayList<>();
        }
        returnMap.put("departments", list);
        return "/emp/add";
    }

    @PostMapping("emp")
    public String addEmp(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("emp/{id}")
    public String updateEmpUI(@PathVariable(value = "id") Integer id, Map<String, Object> returnMap) {
        Collection<Department> list = departmentDao.getDepartments();
        if (list == null) {
            list = new ArrayList<>();
        }
        returnMap.put("departments", list);

        Employee employee = employeeDao.get(id);
        returnMap.put("emp", employee);

        return "/emp/add";
    }

    @PutMapping("emp")
    public String updateEmp(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @DeleteMapping("emp/{id}")
    public String deleteEmp(@PathVariable(value = "id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
