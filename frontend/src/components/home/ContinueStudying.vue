<script setup>
import CourseLink from "@/components/shared/CourseLink.vue";
import ButtonGreen from "@/components/shared/ButtonGreen.vue";

const last_course = {
  name: 'Introduction to ML',
  lessons_passed: 2,
  last_lesson: 'Regression and model quality assessment',
  subtopics: [
    'Linear and polynomial regression',
    'Measurement accuracy models: MSE, R²',
    'Cross-validation and regularization'
  ]
}
</script>

<template>
<div class="continue-course">
  <h3>Continue studying:</h3>
  <div class="course-info">
    <CourseLink
        :title="last_course.last_lesson"
        :lessons_passed="last_course.lessons_passed"
        class="course-link"
    />
    <ul class="subtopics">
      <li v-for="subtopic in last_course.subtopics">
        {{ subtopic }}
      </li>
    </ul>
  </div>
  <div class="go-to-course">
    <router-link to="/course/1">
      <ButtonGreen title="Go to Course" />
    </router-link>
  </div>
</div>
</template>

<style scoped>
.continue-course {
  box-sizing: border-box;
  background-color: #42A5F5;
  margin-top: 15vh;
  padding: 20px 20px;
  border-radius: 16px;
}

h3 {
  font-size: 1.5rem;
  font-weight: bold;
  color: #F5F7FA;
  margin-bottom: 5vh
}

.course-info {
  display: flex;
  align-items: center;
  margin-bottom: 3vh;
}

.course-info .course-link {
  border-radius: 20px;
  width: 18vw;
  height: 13vh;
  margin-right: 3vw;
}

.course-info ul {
  list-style-type: disc;
  width: 12vw;
  font-size: 12px;
  color: #F5F7FA;
  padding: 0;
}

.course-info ul li {
  margin-bottom: 0.5vh;
}

.go-to-course {
  padding-left: 20vw;
}
</style>