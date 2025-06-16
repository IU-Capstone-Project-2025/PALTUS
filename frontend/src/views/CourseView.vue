<script setup>
import SideBar from "@/components/course/SideBar.vue";

const course = {
  "course_name": "Python basics",
  "description": "Optional",
  "books": ["Useful books for the course"],
  "lessons": [
  {
    "lesson_number": 1,
    "title": "Introduction",
    "quiz": false,
    "subtopics": [
      {
        "topic": "Subtopic of the lesson",
        "notes": "",
        "finished": false
      }
    ],
    "links": ["Useful link for learning", "Another useful link for learning", "etc."]
  },
    {
      "lesson_number": 2,
      "title": "Basic Syntax and Variables",
      "quiz": false,
      "subtopics": [
        {
          "topic": "Subtopic of the lesson",
          "notes": "",
          "finished": false
        }
      ],
      "links": ["Useful link for learning", "Another useful link for learning", "etc."]
    },
    {
      "lesson_number": 3,
      "title": "Data Types",
      "quiz": false,
      "subtopics": [
        {
          "topic": "Subtopic of the lesson",
          "notes": "",
          "finished": false
        }
      ],
      "links": ["Useful link for learning", "Another useful link for learning", "etc."]
    },
]
}

const chosenContent = 0;
const lessons_passed = 3;
const lessons_num = 11;

const progress = lessons_passed / lessons_num;
</script>

<template>
  <div class="main">
    <section class="left">
      <SideBar :course="course" :progress="progress" />
    </section>
    <section class="center">

    </section>
  </div>
</template>

<style scoped>
.main {
  display: flex;
}
</style>