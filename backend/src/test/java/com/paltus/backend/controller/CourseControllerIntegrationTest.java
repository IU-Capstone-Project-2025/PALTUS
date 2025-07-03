// package com.paltus.backend.controller;

// import com.paltus.backend.model.Course;
// import com.paltus.backend.repository.CourseRepository;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.Optional;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
// @AutoConfigureMockMvc
// class CourseControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private CourseRepository courseRepository;

//     @Test
//     void getCourseById_ShouldReturnCourse() throws Exception {
//         Course course = new Course();
//         course.setId(1L);
//         course.setCourse_name("Test Course");
        
//         when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        
//         mockMvc.perform(get("/courses/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1))
//                 .andExpect(jsonPath("$.course_name").value("Test Course"));
//     }

//     @Test
//     void getCourseById_ShouldReturn404WhenNotFound() throws Exception {
//         when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        
//         mockMvc.perform(get("/courses/1"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     void saveCourse_ShouldCreateNewCourse() throws Exception {
//         Course course = new Course();
//         course.setId(1L);
//         course.setCourse_name("New Course");
        
//         when(courseRepository.save(any(Course.class))).thenReturn(course);
        
//         mockMvc.perform(post("/courses/saveCourse")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"course_name\":\"New Course\"}"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1))
//                 .andExpect(jsonPath("$.course_name").value("New Course"));
//     }

//     @Test
//     void deleteCourse_ShouldReturn204() throws Exception {
//         when(courseRepository.existsById(1L)).thenReturn(true);
        
//         mockMvc.perform(delete("/courses/1"))
//                 .andExpect(status().isNoContent());
//     }
// }