package com.snowbuffer.study.java.common.jackson.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CourseVo implements Serializable {

    //    private static final long serialVersionUID = 5112902381302318510L;
    private Long courseId;

    private Long schoolId;

    private String courseName;

    private Long subjectId;

    private Long teacherUserId;

    private Optional<Long> tempLong;

    private Boolean isOk;

    private boolean valid;

    // jackson 不依赖setter/getter方法
}