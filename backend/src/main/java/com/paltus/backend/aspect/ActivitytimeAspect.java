package com.paltus.backend.aspect;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.paltus.backend.model.requests.SaveCourseRequest;
import com.paltus.backend.model.requests.SubtopicSetStateRequest;
import com.paltus.backend.repository.CourseRepository;


@Aspect
@Component
public class ActivitytimeAspect {
    private final CourseRepository courseRepository;

    public ActivitytimeAspect(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @After("@annotation(com.paltus.backend.aspect.annotation.UpdateLastActivityTime)")
    public void trackActivity(JoinPoint joinPoint) {
        // Get all the arguments that go with the method
        // nn the case of the PathVariable and Request body request
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof SubtopicSetStateRequest subtopicSetStateRequest) {
                handleActivity(subtopicSetStateRequest.getCourseId());

            } else if (arg instanceof SaveCourseRequest request) {
                handleActivity(request.getCourse().getId());
            }  else if (arg instanceof Map map && map.containsKey("courseId")) {
                handleActivity(Long.parseLong(map.get("courseId").toString()));
            }
        }
    }

    private void handleActivity(Long courseId) {
        if (courseId != null) {
            courseRepository.updateLastActivityTime(courseId, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        }
    }




}
