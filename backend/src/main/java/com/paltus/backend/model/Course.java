package com.paltus.backend.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paltus.backend.converter.StringListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a learning course with lessons, books, and metadata.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String course_name;
    private String description;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "text")
    private List<String> books;

    /** Last time the user studied the course */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Moscow")
    private Instant lastActivityTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonManagedReference
    @OrderBy("id ASC")
    private List<Lesson> lessons;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;


    /**
     * Set bidirectional relationships between course, lessons, and subtopics.
     * Should be called after generating a new course.
     */
    public void setupRelationships() {
        for (Lesson lesson : lessons) {
            lesson.setCourse(this);
            for (Subtopic subtopic : lesson.getSubtopics()) {
                subtopic.setLesson(lesson);
            }
        }
    }

}
