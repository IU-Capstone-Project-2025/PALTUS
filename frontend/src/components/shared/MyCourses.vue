<script setup>
import CourseLink from "@/components/shared/CourseLink.vue";
import axios from "@/plugins/axios.js";
import {onMounted, ref} from "vue";

const courses = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get('courses');
    courses.value = await response;
    console.log(courses);
    for (const course of courses.value) {
      // const course_info = await axios.get('courses/' + course.id);
      course.lessons_passed = 0;
    }
  } catch (err) {
    console.error('Request failed:', err);
  }
})

// const courses = [
//   {
//     name: 'Introduction to ML',
//     lessons_passed: 2
//   },
//   {
//     name: 'Python Programming',
//     lessons_passed: 0,
//   },
//   {
//     name: 'English Advanced',
//     lessons_passed: 7,
//   }
// ]
</script>

<template>
  <div class="my-courses">
    MY COURSES:
  </div>
  <ul class="courses">
    <li v-for="course in courses">
      <CourseLink :title="course.course_name" :lessons_passed="course.lessons_passed" />
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
  font-size: 20px;
  padding-left: 30px;
  background-color: #42A5F5;
  align-content: center;
  color: #F5F7FA;
  font-weight: bold;
  border-bottom-right-radius: 16px;
}
</style>