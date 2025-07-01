// package com.paltus.backend.mapper;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.Collections;
// import java.util.List;

// import org.junit.jupiter.api.Test;

// import com.paltus.backend.dto.CoursePageDto;
// import com.paltus.backend.dto.CourseSummaryDto;
// import com.paltus.backend.dto.LessonDto;
// import com.paltus.backend.mapper.impl.CourseMapperImpl;
// import com.paltus.backend.model.Course;
// import com.paltus.backend.model.Lesson;
// import com.paltus.backend.model.Subtopic;
// import com.paltus.backend.dto.SubtopicDto;

// public class CourseMapperTest {
//     private final CourseMapper mapper = new CourseMapperImpl();

//     @Test
//     void toCourseSummaryDto_MapsCorrectly() {
//         Course course = new Course();
//         course.setId(1);
//         course.setCourse_name("1");

//         CourseSummaryDto dto = mapper.toCourseSummaryDto(course);

//         assertEquals(1, dto.id());
//         assertEquals("1", dto.course_name());
//     }

//     @Test
//     void toCoursePageDto_MapsAllFields() {
//         Course course = new Course();
//         course.setId(1);
//         course.setCourse_name("1");
//         course.setDescription("2");
//         course.setBooks(List.of("Book1", "Book2"));
        
//         Lesson lesson = new Lesson();
//         lesson.setId(10);
//         lesson.setLesson_number(1);
//         lesson.setTitle("1.1");
//         lesson.setQuiz(false);
//         lesson.setLinks(Collections.emptyList());
//         lesson.setSubtopics(Collections.emptyList());
        
//         course.setLessons(List.of(lesson));

//         CoursePageDto dto = mapper.toCoursePageDto(course);

//         assertEquals(1, dto.id());
//         assertEquals("1", dto.course_name());
//         assertEquals("2", dto.description());
//         assertEquals(List.of("Book1", "Book2"), dto.books());
//         assertEquals(1, dto.lessons().size());
//         assertEquals(10, dto.lessons().get(0).id());
//     }

//     // @Test
//     // void toCoursePageDto_NullLessons_ReturnsEmptyList() {
//     //     Course course = new Course();
//     //     course.setId(1);
//     //     course.setLessons(null); 

//     //     CoursePageDto dto = mapper.toCoursePageDto(course);

//     //     assertTrue(dto.lessons().isEmpty());
//     // }

//     @Test
//     void toLessonDto_MapsSubtopicsCorrectly() {
//         Lesson lesson = new Lesson();
//         lesson.setId(1);
//         lesson.setLesson_number(1);
//         lesson.setTitle("1");
//         lesson.setQuiz(true);
//         lesson.setLinks(List.of("link1", "link2"));
        
//         Subtopic subtopic = new Subtopic();
//         subtopic.setId(100);
//         subtopic.setTopic("1.1");
//         subtopic.setNotes("1.2");
//         subtopic.setFinished(false);
        
//         lesson.setSubtopics(List.of(subtopic));

//         LessonDto dto = mapper.toLessonDto(lesson);
        
//         assertEquals(1, dto.id());
//         assertEquals(1, dto.lesson_number());
//         assertEquals("1", dto.title());
//         assertTrue(dto.quiz());
//         assertEquals(List.of("link1", "link2"), dto.links());
//         assertEquals(1, dto.subtopics().size());
//         assertEquals(100, dto.subtopics().get(0).id());
//         assertEquals("1.1", dto.subtopics().get(0).topic());
//     }

//     @Test
//     void toSubtopicDto_MapsCorrectly() {
//         Subtopic subtopic = new Subtopic();
//         subtopic.setId(1);
//         subtopic.setTopic("1");
//         subtopic.setNotes("2");
//         subtopic.setFinished(true);
        
//         SubtopicDto dto = mapper.toSubtopicDto(subtopic);
        
//         assertEquals(1, dto.id());
//         assertEquals("1", dto.topic());
//         assertEquals("2", dto.notes());
//         assertTrue(dto.finished());
//     }
// }
