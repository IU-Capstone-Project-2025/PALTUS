package com.paltus.backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.User;
import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.CourseSummaryDto;
import com.paltus.backend.model.dto.DashboardDto;
import com.paltus.backend.repository.CourseRepository;
import com.paltus.backend.repository.LessonRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;
    private CourseMapper courseMapper;
    private UserService userService;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper,
            LessonRepository lessonRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.lessonRepository = lessonRepository;
        this.userService = userService;
    }

    public Course saveCourse(Course course) {
        course.setupRelationships();
        User user = userService.getCurrentUser();
        course.setUser(user);
        // course.setLastActivityTime(Instant.now());
        Course newCourse = courseRepository.save(course);
        return newCourse;
    }

    public CoursePageDto getCourseById(long course_id) {
        Course course = courseRepository.findById(course_id).orElseThrow(
                () -> new EntityNotFoundException("Course not found with id " + course_id));
        User user = userService.getCurrentUser();
        if (!user.equals(course.getUser())) {
            throw new AccessDeniedException("You don't have this course");
        }
        return courseMapper.toCoursePageDto(course);
    }

    public Lesson getNextLesson(Course course) {
        List<Lesson> lessons = lessonRepository.findUnfinishedLessonsByCourseId(course.getId());
        lessons.sort(Comparator.comparing(Lesson::getId));
        return lessons.get(0);
    }

    public List<CourseSummaryDto> getAllCoursesSummaries(List<Course> courses) {
        // List<Course> courses = courseRepository.findAll();
        // courses.sort(Comparator.comparing(
        // Course::getLastActivityTime,
        // Comparator.nullsLast(Comparator.reverseOrder())));
        List<CourseSummaryDto> coursesDtos = new ArrayList<>();
        for (Course course : courses) {
            coursesDtos.add(courseMapper.toCourseSummaryDto(course, getNextLesson(course).getLesson_number()));
        }
        return coursesDtos;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        courses.sort(Comparator.comparing(
                Course::getLastActivityTime,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return courses;
    }

    public DashboardDto getDashboard() {
        List<Course> courses = getAllCourses();
        Lesson lesson;
        if (courses.isEmpty()) {
            lesson = null;
        } else {
            lesson = getNextLesson(courses.get(0));
        }
        return courseMapper.toDashboardDto(getAllCoursesSummaries(courses), lesson);
    }

    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));

        User user = userService.getCurrentUser();
        if (!user.equals(course.getUser())) {
            throw new AccessDeniedException("You don't have this course");
        }

        courseRepository.deleteById(id);
    }
}
