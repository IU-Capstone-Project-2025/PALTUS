package com.paltus.backend.mapper.impl;

import org.springframework.stereotype.Component;

import com.paltus.backend.dto.CourseSummaryDto;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;

@Component
public class CourseMapperImpl implements CourseMapper {
    @Override
    public CourseSummaryDto toCourseSummaryDto(Course course) {
        return new CourseSummaryDto(course.getId(), course.getCourse_name());
    }
}
