// package com.paltus.backend.controller;

// import com.paltus.backend.dto.CoursePageDto;
// import com.paltus.backend.dto.CourseSummaryDto;
// import com.paltus.backend.service.CourseService;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import java.util.Collections;

// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// public class CourseControllerTest {

//     @Mock
//     private CourseService courseService;

//     @InjectMocks
//     private CourseController courseController;

//     private MockMvc mockMvc;

//     @BeforeEach
//     public void setup() {
//         mockMvc = MockMvcBuilders.standaloneSetup(courseController).build(); 
//     }

    // @Test
    // public void testGetAllCoursesSummaries() throws Exception {
    //     CourseSummaryDto courseSummary = new CourseSummaryDto(1L, "Test Course");
    //     when(courseService.getAllCoursesSummaries()).thenReturn(Collections.singletonList(courseSummary));
    //     mockMvc.perform(get("/courses"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0].id").value(1))
    //             .andExpect(jsonPath("$[0].course_name").value("Test Course"));

    //     verify(courseService, times(1)).getAllCoursesSummaries();
    // }

    // @Test
    // public void testGetCourseById() throws Exception {
    //     CoursePageDto coursePageDto = new CoursePageDto(1L, "Test Course", "Description", Collections.emptyList(), Collections.emptyList());
    //     when(courseService.getCourseById(1L)).thenReturn(coursePageDto);
    //     mockMvc.perform(get("/courses/{id}", 1))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value(1))
    //             .andExpect(jsonPath("$.course_name").value("Test Course"));

    //     verify(courseService, times(1)).getCourseById(1L);
    // }

    // @Test
    // public void testDeleteCourseById() throws Exception {
    //     mockMvc.perform(delete("/courses/{id}", 1))
    //             .andExpect(status().isNoContent());
    //     verify(courseService, times(1)).deleteCourse(1L);
    // }
// }
