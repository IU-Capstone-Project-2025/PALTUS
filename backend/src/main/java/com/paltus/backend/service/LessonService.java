package com.paltus.backend.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.paltus.backend.model.dto.LessonContextDto;
import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.repository.LessonRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private CourseMapper courseMapper;
    private AchievementService achievementService;

    public LessonService(LessonRepository lessonRepository, CourseMapper courseMapper, AchievementService achievementService) {
        this.lessonRepository = lessonRepository;
        this.courseMapper = courseMapper;
        this.achievementService = achievementService;
    }

    public LessonDto getLessonById(long lesson_id) {
        Lesson lesson = this.lessonRepository.findById(lesson_id).get();
        return this.courseMapper.toLessonDto(lesson);
    }

    /**
     * Updates lesson finished state depending on whether all subtopics are finished.
     * Also updates achievement progress if lesson is completed.
     */
    public void handleSubtopicFinished(long id) {
        if (lessonRepository.isFinished(id)) {
            lessonRepository.updateLessonFinishedState(id, true);
            achievementService.updateProgress(AchievementType.COMPLETE_LESSONS);
        } else {
            lessonRepository.updateLessonFinishedState(id, false);
        }
    }

    /**
     * Marks the quiz for the given lesson ID as passed.
     * Updates achievement progress for quizzes.
     * Throws EntityNotFoundException if lesson does not exist.
     */
    public void setQuizAsPassed(long id) {
        if (!lessonRepository.existsById(id)) {
            throw new EntityNotFoundException("No subtopic with id " + id);
        }
        lessonRepository.setQuizAsPassed(id);
        achievementService.updateProgress(AchievementType.COMPLETE_QUIZZES);
    }

    /**
     * Retrieves lesson context including course name, lesson title,
     * and a list of subtopics.
     * Throws RuntimeException if lesson not found.
     */
    public LessonContextDto getLessonContext(Long id) {
        Lesson lesson = this.lessonRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

        String courseName = lesson.getCourse().getCourse_name();
        String lessonName = lesson.getTitle();

        return new LessonContextDto(
        courseName,
        lessonName,
        lesson.getSubtopics()
            .stream()
            .map(courseMapper::toSubtopicDto)
            .collect(Collectors.toCollection(ArrayList::new))
        );
    }
}
