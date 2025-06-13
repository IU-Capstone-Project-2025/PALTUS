package com.paltus.backend.model;

import java.util.List;

public class Course {
    private String course_name;
    private String description;
    private List<String> books;
    private List<Lesson> lessons;

    public String getCourse_name() { return course_name; }
    public void setCourse_name(String course_name) { this.course_name = course_name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getBooks() { return books; }
    public void setBooks(List<String> books) { this.books = books; }

    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }

    public static class Lesson {
        private int lesson_number;
        private String title;
        private boolean quiz;
        private List<Subtopic> subtopics;
        private List<String> links;

        public int getLesson_number() { return lesson_number; }
        public void setLesson_number(int lesson_number) { this.lesson_number = lesson_number; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public boolean isQuiz() { return quiz; }
        public void setQuiz(boolean quiz) { this.quiz = quiz; }

        public List<Subtopic> getSubtopics() { return subtopics; }
        public void setSubtopics(List<Subtopic> subtopics) { this.subtopics = subtopics; }

        public List<String> getLinks() { return links; }
        public void setLinks(List<String> links) { this.links = links; }
    }

    public static class Subtopic {
        private String topic;
        private String notes;
        private boolean finished;

        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        public boolean isFinished() { return finished; }
        public void setFinished(boolean finished) { this.finished = finished; }
    }
}
