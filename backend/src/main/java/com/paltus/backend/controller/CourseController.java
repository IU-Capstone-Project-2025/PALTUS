package com.paltus.backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.aspect.annotation.UpdateLastActivityTime;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.DashboardDto;
import com.paltus.backend.model.requests.SaveCourseRequest;
import com.paltus.backend.service.ChatService;
import com.paltus.backend.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;
    private ChatService chatService;

    public CourseController(CourseService courseService, ChatService chatService) {
        this.chatService = chatService;
        this.courseService = courseService;
    }


    @Operation(
        description = "Retrieve the dashboard data for the user"
    )
    @GetMapping()
    public DashboardDto getDashboardDto() {
        return courseService.getDashboard();
    }
    
    @Operation(
        description = "Retrieve detailed information about a course by its ID"
    )
    @GetMapping("/{id}")
    public CoursePageDto getCourseById(@PathVariable(name = "id") long id) {
        return courseService.getCourseById(id);
    }

    @Operation(description = "Delete the course with the specified ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Save a course and deletes the related chat session")
    @PostMapping("/saveCourse")
    @UpdateLastActivityTime
    public Course postMethodName(@RequestBody SaveCourseRequest request) {
        courseService.saveCourse(request.getCourse());
        chatService.deleteSession(request.getSessionId());
        return request.getCourse();
    }

}
