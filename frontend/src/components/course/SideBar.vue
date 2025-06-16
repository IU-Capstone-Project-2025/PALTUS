<script setup>
import ProgressBar from "@/components/course/ProgressBar.vue";
import Logo from "@/components/shared/Logo.vue";

defineProps({
  course: {
    type: Object,
    required: true,
  },
  progress: {
    type: Number,
    required: true,
  }
})


</script>

<template>
  <div class="side-bar">
    <Logo class="courses-logo" />
    <ProgressBar :fraction_finished="progress * 100" />
    <div class="course-name">
      {{ course.course_name }}
    </div>
    <ul class="course-list">
      <li class="lesson-item" v-for="lesson in course.lessons">
        <div class="lesson">
          {{ `${lesson.lesson_number}. ${lesson.title}` }}
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

.side-bar {
  width: 23vw;
  height: 100vh;
  background-color: #42A5F5;
}

.courses-logo {
  background-color: #42A5F5;
  color: #F5F7FA;
}

.course-name {
  color: #F5F7FA;
  text-transform: uppercase;
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-sizing: border-box;
  padding-left: 2vw;
  height: 12vh;
  font-size: 20px;
  font-weight: 600;
}

.lesson {
  color: #F5F7FA;
  padding: 3vh 2vw;
  font-size: 18px;
  background-color: #42A5F5;
}
</style>