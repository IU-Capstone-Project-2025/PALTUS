<script setup>
import SideBar from "@/components/course/SideBar.vue";
import {computed, onMounted, ref, watch} from "vue";
import {useCourseStore} from "@/stores/course.js";
import {onBeforeRouteLeave, useRoute} from "vue-router";
import Content from "@/components/course/Content.vue";


const route = useRoute();
const course = useCourseStore();
let subtopicsChanged = [];

const lessons_num = computed(() => course.lessons.length);

const lessons_passed = computed(() => {
  let passed = 0;
  course.lessons.forEach(lesson => {
    if (lesson.subtopics.every(subtopic => subtopic.finished)) {
      passed++;
    }
  });
  return passed;
});

let progress = computed(() => {
  return lessons_passed.value > 0 ? lessons_passed.value / lessons_num.value : lessons_passed.value;
});

onMounted(() => {
  const course_id = route.params.id;
  course.loadCourse(course_id);
  console.log(course);
});

const chosenContent = ref(0);

watch(chosenContent, async (newValue, oldValue) => {
  if (oldValue) {
    for (const subtopicChanged of subtopicsChanged) {
      await course.updateSubtopic(subtopicChanged);
    }
    subtopicsChanged = [];
  }
})

onBeforeRouteLeave(async (to, from, next) => {
  if (subtopicsChanged.length > 0) {
    for (const subtopicChanged of subtopicsChanged) {
      await course.updateSubtopic(subtopicChanged);
    }
    subtopicsChanged = [];
  }
  next();
});
</script>

<template>
  <div class="main">
    <section class="left">
      <SideBar :course="course" :progress="progress" v-model="chosenContent" />
    </section>
    <section class="center">
      <Content
        :course="course"
        :chosenContent="chosenContent"
        :subtopicsChanged="subtopicsChanged"
        class="course-content"
      />
    </section>
  </div>
</template>

<style scoped>
ul {
  padding-left: 1.5vw;
  color: #0D47A1;
}

.main {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.left {
  height: 100%;
  overflow-y: scroll;
  scrollbar-width: none;
  box-sizing: border-box;
  flex-shrink: 0;
}
</style>