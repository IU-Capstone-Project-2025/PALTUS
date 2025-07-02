    // package com.paltus.backend.service;

    // import com.paltus.backend.exception.EntityNotFoundException;
    // import com.paltus.backend.model.Course;
    // import com.paltus.backend.model.Lesson;
    // import com.paltus.backend.model.dto.CoursePageDto;
    // import com.paltus.backend.model.dto.CourseSummaryDto;
    // import com.paltus.backend.model.dto.DashboardDto;
    // import com.paltus.backend.repository.CourseRepository;
    // import com.paltus.backend.repository.LessonRepository;
    // import com.paltus.backend.mapper.CourseMapper;
    // import org.junit.jupiter.api.BeforeEach;
    // import org.junit.jupiter.api.Test;
    // import org.junit.jupiter.api.extension.ExtendWith;
    // import org.mockito.InjectMocks;
    // import org.mockito.Mock;
    // import org.mockito.junit.jupiter.MockitoExtension;

    // import java.time.Instant;
    // import java.util.Collections;
    // import java.util.List;
    // import java.util.Optional;

    // import static org.junit.jupiter.api.Assertions.*;
    // import static org.mockito.ArgumentMatchers.any;
    // import static org.mockito.ArgumentMatchers.anyLong;
    // import static org.mockito.Mockito.*;

    // @ExtendWith(MockitoExtension.class)
    // class CourseServiceTest {

    //     @Mock
    //     private CourseRepository courseRepository;
        
    //     @Mock
    //     private LessonRepository lessonRepository;
        
    //     @Mock
    //     private CourseMapper courseMapper;
        
    //     @InjectMocks
    //     private CourseService courseService;

    //     private Course createTestCourse(Long id) {
    //         Course course = new Course();
    //         course.setId(id);
    //         course.setCourse_name("Course " + id);
    //         course.setLastActivityTime(Instant.now());
    //         return course;
    //     }

    //     private Lesson createTestLesson(Long id, int number) {
    //         Lesson lesson = new Lesson();
    //         lesson.setId(id);
    //         lesson.setLesson_number(number);
    //         lesson.setTitle("Lesson " + number);
    //         return lesson;
    //     }

    //     @Test
    //     void saveCourse_ShouldCallRepositorySaveAndSetupRelationships() {
    //         Course course = createTestCourse(1L);
            
    //         when(courseRepository.save(course)).thenReturn(course);
            
    //         Course saved = courseService.saveCourse(course);
            
    //         assertEquals(1L, saved.getId());
    //         verify(courseRepository, times(1)).save(course);
    //     }

    //     @Test
    //     void getCourseById_ShouldReturnCoursePageDto() {
    //         Course course = createTestCourse(1L);
    //         CoursePageDto expectedDto = new CoursePageDto(1L, "Test", "Desc", Collections.emptyList(), Collections.emptyList());
            
    //         when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
    //         when(courseMapper.toCoursePageDto(course)).thenReturn(expectedDto);
            
    //         CoursePageDto result = courseService.getCourseById(1L);
    //         assertEquals(1L, result.id());
    //         assertEquals("Test", result.course_name());
    //     }

    //     @Test
    //     void getNextLesson_ShouldReturnFirstUnfinishedLesson() {
    //         Course course = createTestCourse(1L);
            
    //         Lesson lesson1 = createTestLesson(2L, 2);
    //         Lesson lesson2 = createTestLesson(1L, 1);
            
    //         when(lessonRepository.findUnfinishedLessonsByCourseId(1L))
    //             .thenReturn(List.of(lesson1, lesson2));
            
    //         Lesson nextLesson = courseService.getNextLesson(course);
    //         assertEquals(1L, nextLesson.getId());
    //         assertEquals(1, nextLesson.getLesson_number());
    //     }

    //     @Test
    //     void getNextLesson_ShouldReturnNullWhenNoUnfinished() {
    //         Course course = createTestCourse(1L);
            
    //         when(lessonRepository.findUnfinishedLessonsByCourseId(1L))
    //             .thenReturn(Collections.emptyList());
            
    //         Lesson result = courseService.getNextLesson(course);
    //         assertNull(result);
    //     }

    //     @Test
    //     void getAllCourses_ShouldReturnSortedList() {
    //         Course course1 = createTestCourse(1L);
    //         course1.setLastActivityTime(Instant.now().minusSeconds(100));
            
    //         Course course2 = createTestCourse(2L);
    //         course2.setLastActivityTime(Instant.now());
            
    //         when(courseRepository.findAll()).thenReturn(List.of(course1, course2));
            
    //         List<Course> result = courseService.getAllCourses();
    //         assertEquals(2, result.size());
    //         assertEquals(2L, result.get(0).getId());
    //         assertEquals(1L, result.get(1).getId());
    //     }

    //     @Test
    //     void getDashboard_ShouldReturnDashboardDto() {
    //         Course course = createTestCourse(1L);
    //         Lesson lesson = createTestLesson(10L, 1);
            
    //         when(courseRepository.findAll()).thenReturn(List.of(course));
    //         when(lessonRepository.findUnfinishedLessonsByCourseId(1L)).thenReturn(List.of(lesson));
    //         when(courseMapper.toCourseSummaryDto(any(), anyInt())).thenReturn(
    //             new CourseSummaryDto(1L, "Course", 1)
    //         );
    //         when(courseMapper.toDashboardDto(anyList(), any())).thenReturn(
    //             new DashboardDto(List.of(new CourseSummaryDto(1L, "Course", 1)), null)
    //         );
            
    //         DashboardDto result = courseService.getDashboard();
    //         assertNotNull(result);
    //         assertEquals(1, result.courses().size());
    //     }

    //     @Test
    //     void deleteCourse_ShouldCallDeleteWhenExists() {
    //         when(courseRepository.existsById(1L)).thenReturn(true);
            
    //         courseService.deleteCourse(1L);
            
    //         verify(courseRepository, times(1)).deleteById(1L);
    //     }
    // }