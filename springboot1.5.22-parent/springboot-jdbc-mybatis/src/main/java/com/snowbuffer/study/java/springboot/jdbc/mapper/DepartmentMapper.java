package com.snowbuffer.study.java.springboot.jdbc.mapper;

import com.snowbuffer.study.java.springboot.jdbc.domain.Department;
import org.apache.ibatis.annotations.*;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-09-22 22:01
 */
public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    Department getDepartById(Integer id);

    @Delete("delete from department where id = #{id}")
    int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(department_name) values(#{departmentName})")
    int insertDepart(Department department);

    @Update("update department set department_name = #{departmentName} where id = #{id}")
    int updateDept(Department department);
}
