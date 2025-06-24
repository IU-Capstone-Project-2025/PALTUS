<script setup>
import CourseLink from "@/components/shared/CourseLink.vue";
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import {computed} from "vue";

const props = defineProps({
  courseId: {
    type: Number,
    required: true,
  },
  lessonTitle: {
    type: String,
    required: true,
  },
  subtopics: {
    type: Array,
    required: true,
  }
});

const link = computed(() => `course/${props.courseId}`);
</script>

<template>
<div class="continue-course">
  <h3>Continue studying:</h3>
  <div class="course-info">
    <CourseLink
        v-if="props.courseId"
        :title="props.lessonTitle"
        :id="props.courseId"
        class="course-link"
    />
    <ul class="subtopics">
      <li v-for="subtopic in props.subtopics">
        {{ subtopic.topicName }}
      </li>
    </ul>
  </div>
  <div class="go-to-course">
    <router-link :to="link">
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