package com.snowbuffer.study.java.common.jackson.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.util.Optional;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-29 21:15
 */
public class CourseVoTest {

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        CourseVo courseVo = new CourseVo();
        courseVo.setCourseId(0L);
        courseVo.setSchoolId(0L);
        courseVo.setCourseName(null);
        courseVo.setSubjectId(0L);
        courseVo.setTeacherUserId(0L);
        courseVo.setTempLong(Optional.empty());
        courseVo.setIsOk(true);

        System.out.println(objectMapper.writeValueAsString(courseVo));
    }
}