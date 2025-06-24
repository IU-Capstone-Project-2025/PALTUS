<script setup>
import SideBar from "@/components/course/SideBar.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import {computed, onMounted, reactive, ref} from "vue";
import mock_course from "../../public/course.js";
import BaseCheckbox from "@/components/shared/BaseCheckbox.vue";
import {useCourseStore} from "@/stores/course.js";
import {useRoute} from "vue-router";

const route = useRoute();
const course = useCourseStore();
let progress = ref(0);

onMounted(() => {
  const courseId = route.params.id;
  course.loadCourse(courseId);
  console.log(course);
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
  progress = computed(() => {
    return lessons_passed.value > 0 ? lessons_passed.value / lessons_num.value : lessons_passed.value;
  });
});

const chosenContent = ref(0);
</script>

<template>
  <div class="main">
    <section class="left">
      <SideBar :course="course" :progress="progress" v-model="chosenContent" />
    </section>
    <section class="center">
      <div class="lesson-content" v-if="chosenContent">
        <BaseHeader :text="course.lessons[chosenContent - 1].title + ':'" class="uppercase" />
        <div class="subtopics">
          <div class="field-name">
            Subtopics:
          </div>
          <ul class="subtopics-list">
            <li v-for="subtopic in course.lessons[chosenContent - 1].subtopics" class="subtopic">
              <BaseCheckbox :id="subtopic.topicName" v-model="subtopic.finished" />
              <label :for="subtopic.topic" class="field-info">{{ subtopic.topicName }}</label>
            </li>
          </ul>
        </div>
        <div class="books">
          <div class="field-name">
            Useful links:
          </div>
          <ul>
            <li v-for="link in course.lessons[chosenContent - 1].links" class="field-info">
              <p> {{ link }}</p>
            </li>
          </ul>
        </div>
      </div>
      <div class="lesson-content" v-if="!chosenContent">
        <BaseHeader :text="course.course_name" class="uppercase" />
        <div class="description">
          <h1 class="field-name">
            Description:
          </h1>
          <p class="field-info">{{ course.description }}</p>
        </div>
        <div class="books">
          <div class="field-name">
            Useful books for the course:
          </div>
          <ul>
            <li v-for="book in course.books" class="field-info">
              <p> {{ book }}</p>
            </li>
          </ul>
        </div>
      </div>
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
  box-sizing: border-box;
}

.lesson-content {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  padding: 2vh 30vw 2vh 8vw;
}

.uppercase {
  text-transform: uppercase;
  font-size: 36px;
  margin-bottom: 7vh;
}

.field-name {
  font-size: 28px;
  font-weight: bold;
  color: #0D47A1;
  margin-bottom: 1vh;
}

.field-info {
  font-size: 20px;
  color: #0D47A1;
  margin-bottom: 1vh;
}

.description {
  margin-bottom: 4vh;
}

.subtopics {
  margin-bottom: 4vh;
}

.subtopics-list {
  padding: 0;
  list-style: none;
}

.subtopic {
  display: flex;
}
</style>