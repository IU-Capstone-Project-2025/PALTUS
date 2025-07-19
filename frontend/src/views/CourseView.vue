<script setup>
/**
 * CourseView.vue - course page,
 * available only with authorization, shows all the lessons content and course information
 */
import SideBar from "@/components/course/SideBar.vue";
import {computed, onBeforeMount, reactive, ref, watch} from "vue";
import {useCourseStore} from "@/stores/course.js";
import {onBeforeRouteLeave, useRoute} from "vue-router";
import ContentPage from "@/components/course/ContentPage.vue";


const route = useRoute();
const course = useCourseStore();
let subtopicsChanged = reactive([]);

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

onBeforeMount(async () => {
  const course_id = route.params.id;
  await course.loadCourse(course_id);

  if (route.query.lessonTitle) {
    chosenContent.value = course.lessons.find(lesson => lesson.title === route.query.lessonTitle).lesson_number;
  }
});

const chosenContent = ref(0);

watch(chosenContent, async (newValue, oldValue) => {
  if (oldValue) {
    for (const subtopicChanged of subtopicsChanged) {
      await course.updateSubtopic(subtopicChanged, course.lessons[chosenContent.value - 1].id);
      await course.loadCourse(course.courseId);
    }
    subtopicsChanged = [];
  }
})

onBeforeRouteLeave(async (to, from, next) => {
  if (subtopicsChanged.length > 0) {
    for (const subtopicChanged of subtopicsChanged) {
      await course.updateSubtopic(subtopicChanged, course.lessons[chosenContent.value - 1].id);
    }
    subtopicsChanged = [];
  }
  next();
});
</script>

<template>
  <div class="main">
    <section class="left">
      <SideBar v-model="chosenContent" :course="course" :progress="progress"/>
    </section>
    <section class="center">
      <ContentPage
          :chosenContent="chosenContent"
          :course="course"
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
  position: relative;
}

.left {
  position: sticky;
  top: 0;
  height: 100vh;
  align-self: flex-start;
}

.center {
  flex-grow: 1;
  overflow-y: auto;
}
</style>