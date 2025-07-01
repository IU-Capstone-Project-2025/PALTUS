package com.paltus.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.DashboardDto;
import com.paltus.backend.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void getDashboardDto_ShouldReturnDashboard() throws Exception {
        DashboardDto dashboardDto = new DashboardDto(Collections.emptyList(), null);
        when(courseService.getDashboard()).thenReturn(dashboardDto);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses").isArray());
        
        verify(courseService, times(1)).getDashboard();
    }

    @Test
    void getCourseById_ShouldReturnCourse() throws Exception {
        CoursePageDto coursePageDto = new CoursePageDto(1, "Test Course", "Description", 
                Collections.emptyList(), Collections.emptyList());
        
        when(courseService.getCourseById(1)).thenReturn(coursePageDto);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.course_name").value("Test Course"));
    }

    @Test
    void deleteCourseById_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isNoContent());
        
        verify(courseService, times(1)).deleteCourse(1L);
    }

    @Test
    void saveCourse_ShouldSaveAndReturnCourse() throws Exception {
        Course course = new Course();
        course.setId(1);
        course.setCourse_name("New Course");
        
        when(courseService.saveCourse(any(Course.class))).thenReturn(course);
        
        mockMvc.perform(post("/courses/saveCourse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.course_name").value("New Course"));
    }
}