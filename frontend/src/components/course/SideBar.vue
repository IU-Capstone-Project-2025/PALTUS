<script setup>
import ProgressBar from "@/components/shared/ProgressBar.vue";
import Logo from "@/components/shared/Logo.vue";
import LessonsSideBar from "@/components/course/LessonsSideBar.vue";

const props = defineProps({
  course: {
    type: Object,
    required: true,
  },
  progress: {
    type: Number,
    required: true,
  },
  modelValue: {
    type: Number,
    required: true,
  }
});

const emit = defineEmits(['update:modelValue'])
</script>

<template>
  <div class="side-bar">
    <div style="position: sticky; top: 0;">
      <Logo class="courses-logo" />
      <ProgressBar :fraction_finished="progress * 100" class="side-progress" />
    </div>
    <div
        class="course-name"
        @click="emit('update:modelValue', 0)"
    >
      {{ course.course_name }}
    </div>
   <LessonsSideBar :lessons="course.lesssons" />
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
  overflow-y: scroll;
  scrollbar-width: none;
  box-sizing: border-box;
  flex-shrink: 0;
}

.courses-logo {
  background-color: #42A5F5;
  color: #F5F7FA;
  width: 23vw;
  padding-right: 8vw;
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
  font-size: 1.25rem;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
}
.course-name:hover {
  background-color: #0D47A1;
}

.lesson {
  color: #F5F7FA;
  padding: 3vh 2vw;
  font-size: 1.125rem;
  background-color: #42A5F5;
  cursor: pointer;
  transition: 0.2s;
}
.lesson:hover {
  background-color: #0D47A1;
}

.lesson-chosen {
  color: #F5F7FA;
  padding: 3vh 2vw;
  font-size: 1.125rem;
  background-color: #0D47A1;
  cursor: pointer;
  transition: 0.2s;
  font-weight: bold;
}

.side-progress {
  border-right-style: solid;
  border-right-width: 2px;
  border-right-color: #42A5F5;
  border-bottom-style: solid;
  border-bottom-width: 2px;
  border-bottom-color: #42A5F5;
}
</style>