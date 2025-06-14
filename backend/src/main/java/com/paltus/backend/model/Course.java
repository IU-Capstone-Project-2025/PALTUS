package com.paltus.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonManagedReference
    private List<Lesson> lessons;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @JsonManagedReference
    private List<Book> books;

    public void setupRelationships() {
        for (Lesson lesson : lessons) {
            lesson.setCourse(this);
            for (Subtopic subtopic : lesson.getSubtopics()) {
                subtopic.setLesson(lesson);
            }
            for (Link link : lesson.getLinks()) {
                link.setLesson(lesson);
            }
        }

        for (Book book : books) {
            book.setCourse(this);
        }
    }

}
