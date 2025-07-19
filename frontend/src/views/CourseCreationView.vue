<script setup>
import Logo from "@/components/shared/Logo.vue";
import MyCourses from "@/components/shared/MyCourses.vue";
import Account from "@/components/shared/Account.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import {onMounted, reactive, ref} from "vue";
import router from "@/router/index.js";
import {useRoute} from 'vue-router';
import axios from "@/plugins/axios.js"
import EditCourseModal from "@/components/course_creation/EditCourseModal.vue";
import BaseTextArea from "@/components/shared/BaseTextArea.vue";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";
import BaseButton from "@/components/shared/BaseButton.vue";

const route = useRoute();
const name = ref(route.query.courseName || '');
const currentSkills = ref('');
const goal = ref('');
const lessonsNum = ref('');
const duration = ref('');
const courses = ref([]);
const waiting = ref(false);
const showModal = ref(false);
const course = reactive({});
const previous_course = reactive({});
const sessionId = ref('');
const isError = ref(false);
const error_message = ref('');

onMounted(async () => {
  try {
    const response = await axios.get('courses');
    courses.value = response.courses;
  } catch (err) {
    console.error('Request failed:', err);
  }
})

const inputs = ref([
      {
        question: 'Create a new course:',
        placeholder: 'Course topic...',
        model: name,
      }, {
        question: 'What is your current knowledge about this topic?',
        placeholder: 'I know...',
        model: currentSkills,
      }, {
        question: 'What is your goal?',
        placeholder: 'I want to learn...',
        model: goal,
      }, {
        question: 'How many lessons and what is the duration of the lesson?',
        placeholder: 'Number of lessons',
        model: lessonsNum,
      }
    ]
);

const validation = () => {
  const lessonsNumInt = parseInt(lessonsNum.value);
  const durationInt = parseInt(duration.value);
  if (name.value.length > 0
      && currentSkills.value.length > 0
      && goal.value.length > 0
      && lessonsNumInt
      && durationInt
      && lessonsNumInt >= 5
      && lessonsNumInt <= 16
      && durationInt >= 20
      && durationInt <= 240) {
    return 1;
  }
  return 0;
}

const getCourse = async () => {
  isError.value = false;
  waiting.value = true;
  if (validation()) {
    const newCourse = {
      courseName: name.value,
      goal: goal.value,
      knowledge: currentSkills.value,
      lessons: lessonsNum.value,
      time: duration.value,
    };
    try {
      await axios.post(`createCourse`, newCourse).then((response) => {
        course.value = response.course;
        sessionId.value = response.sessionId;
        showModal.value = true;
      });
    } catch (error) {
      if (error.response.status === 406) {
        waiting.value = false;
        error_message.value = 'Your inputs are unacceptable';
        isError.value = true;
      } else if (error.response.status === 500) {
        waiting.value = false;
        error_message.value = 'Internal Server Error, try again later';
        isError.value = true;
      }
    }
  } else {
    return 0;
  }
}

const saveCourse = async () => {
  try {
    const response = {
      course: course.value,
      sessionId: sessionId.value,
    };
    axios.post('courses/saveCourse', response)
        .then((router.push('/')));
  } catch (error) {
    console.err(error);
  }
}

const editCourse = async (prompt) => {
  showModal.value = false;
  try {
    const responseData = {
      request: prompt,
      sessionId: sessionId.value,
    };
    const response = await axios.post('editCourse', responseData);
    previous_course.value = course.value;
    course.value = response.course;
    showModal.value = true;
  } catch (error) {
    if (error.response.status === 406) {
      waiting.value = false;
      error_message.value = 'Your inputs are unacceptable';
      isError.value = true;
    } else if (error.response.status === 500) {
      waiting.value = false;
      error_message.value = 'Internal Server Error, try again later';
      isError.value = true;
    }
  }
}

const savePrevious = async () => {
  if (previous_course.value) {
    try {
      const response = {
        course: previous_course.value,
        sessionId: sessionId.value,
      };
      axios.post('courses/saveCourse', response)
          .then((router.push('/')));
    } catch (error) {
      console.err(error);
    }
  }
}
</script>

<template>
  <div v-if="showModal" class="modal-container">
    <EditCourseModal
        :course="course.value"
        :previous_course="!!previous_course.value"
        @editCourse="editCourse"
        @saveCourse="saveCourse"
        @savePrevious="savePrevious"
    />
  </div>
  <div class="main">
    <section class="left">
      <Logo/>
      <MyCourses :courses="courses"/>
    </section>
    <section class="center">
      <div
          v-for="inp in inputs"
          class="question"
      >
        <BaseHeader :text="inp.question" class="header"/>
        <BaseTextArea v-model="inp.model" :placeholder="inp.placeholder"/>
      </div>
      <div class="question" style="margin-top: 1vh">
        <BaseTextArea v-model="duration" placeholder="Lesson duration (in minutes)"/>
      </div>
      <ErrorNotification v-if="isError" :error_message="error_message"/>
      <BaseButton
          v-if="validation() && !waiting"
          class="increased-size"
          color="green"
          title="GET A COURSE"
          @click="getCourse"
      />
      <BaseButton
          v-if="!validation() && !waiting"
          class="increased-size"
          color="inactive"
          title="GET A COURSE"
      />
      <BaseHeader v-if="waiting" class="waiting" text="Waiting for server response..."/>
    </section>
    <section class="right">
      <Account/>
    </section>
  </div>
</template>

<style scoped>
.main {
  display: flex;
}

.left {
  width: 30vw
}

.center {
  display: flex;
  flex-direction: column;
  width: 40vw;
  align-items: center;
  margin-top: 2vh;
}

.question {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  width: 20vw;
  margin-top: 3vh;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 30vw;
}

.increased-size {
  margin: 2.5vh 0;
  padding: 2vh 3vw;
}

.waiting {
  margin-top: 2.5vh;
}
</style>