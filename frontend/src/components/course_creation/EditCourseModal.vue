<script setup>
import course from "../../../public/course.js";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import {ref} from "vue";

// const props = defineProps({
//   course: {
//     type: Object,
//     required: true
//   }
// })
const prompt = ref('');
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <BaseHeader :text="'You can change a course ' + course.course_name + ' adding new lesson or subtopic'" class="course-name" />
      <ul>
        <li v-for="lesson in course.lessons">
          <p> {{ lesson.lesson_number }}. {{ lesson.title }}</p>
          <ul>
            <li v-for="subtopic in lesson.subtopics">
              <p> {{ subtopic.topic }}</p>
            </li>
          </ul>
        </li>
      </ul>
      <BaseInput placeholder="Prompt for changing a course" model-value="" />
      <ButtonGreen title="Save Course" />
      <ButtonGreen title="Cancel Course" />
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100vw; height: 100vh;
  background: rgba(0,0,0,0.5);
  display: flex; justify-content: center; align-items: center;
  z-index: 5;
}

.modal-content {
  background: #F5F7FA;
  padding: 3vh 2vw;
  border-radius: 10px;
  width: 50vw;
  height: 80vh;
  scroll-behavior: smooth;
}

.course-name {
}

ul {
  list-style-type: none;
}

p {
  color: #0D47A1;
  font-size: 1rem;
}

.course-name {
  text-align: center;
}
</style>