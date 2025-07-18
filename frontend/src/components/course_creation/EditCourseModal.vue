<script setup>
import BaseHeader from "@/components/shared/BaseHeader.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import {ref} from "vue";
import BaseButton from "@/components/shared/BaseButton.vue";

const props = defineProps({
  course: {
    type: Object,
    required: true
  },
  previous_course: {
    type: Boolean,
    required: true
  }
})

defineEmits(['saveCourse', 'editCourse', 'savePrevious']);
const prompt = ref('');
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <BaseHeader text="You can edit a course by adding a new lesson or a subtopic" />
      <div class="modal-body">
        <div class="course-name">
          <BaseHeader :text="props.course.course_name" style="margin-bottom: 2vh" />
        </div>
        <ul>
          <li v-for="lesson in props.course.lessons">
            <p style="font-weight: bold"> {{ lesson.lesson_number }}. {{ lesson.title }}</p>
            <ul>
              <li v-for="subtopic in lesson.subtopics">
                <p> {{ subtopic.topic }}</p>
              </li>
            </ul>
          </li>
        </ul>
      </div>
      <BaseInput placeholder="Prompt for changing a course" v-model="prompt" class="modal-prompt" />
      <BaseButton color="green" title="Edit course" class="modal-btn" @click="$emit('editCourse', prompt)" />
      <BaseButton color="green" title="Save Course" class="modal-btn" @click="$emit('saveCourse')" />
      <BaseButton color="red" title="Cancel edition" class="modal-btn" @click="$emit('savePrevious')" v-if="previous_course" />
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100vw; height: 100vh;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 5;
}

.modal-content {
  background: #F5F7FA;
  box-sizing: border-box;
  padding: 3vh 2vw;
  border-radius: 10px;
  width: 60vw;
  height: 90vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  overflow-x: hidden;
}

.modal-body {
  width: 100%;
  margin: 3vh 0 3vh 8vw;
}

.modal-prompt {
  margin-bottom: 1vh;
}

.course-name {
  text-transform: uppercase;
  font-size: 2rem;
  width: 100%;
  text-align: left;
  margin-top: 2vh;
}

.modal-btn {
  width: 12vw;
  margin: 1vh 0;
}

ul {
  list-style-type: none;
  text-align: left;
  padding: 0;
}

ul ul {
  padding: 0 2vw;
}

p {
  color: #0D47A1;
  font-size: 1rem;
}
</style>