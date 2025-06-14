package com.paltus.backend.mapper;

import com.paltus.backend.dto.CourseSummaryDto;
import com.paltus.backend.model.Course;

public interface CourseMapper {
    CourseSummaryDto toCourseSummaryDto(Course course); 
}
