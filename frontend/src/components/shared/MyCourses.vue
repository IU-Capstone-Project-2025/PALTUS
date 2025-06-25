<script setup>
import CourseLink from "@/components/shared/CourseLink.vue";

const props = defineProps({
  courses: {
    type: Array,
    required: true,
  }
})
</script>

<template>
  <div class="my-courses">
    MY COURSES:
  </div>
  <ul class="courses">
    <li v-for="course in props.courses">
      <CourseLink :title="course.course_name" :next_lesson="course.nextLesson" :id="course.id"/>
    </li>
  </ul>
</template>

<style scoped>
ul{
  list-style-type: none;
  padding: 0;
}

.my-courses {
  box-sizing: border-box;
  width: 23vw;
  height: 10vh;
  font-size: 1.25rem;
  padding-left: 30px;
  background-color: #42A5F5;
  align-content: center;
  color: #F5F7FA;
  font-weight: bold;
  border-bottom-right-radius: 16px;
}
</style>