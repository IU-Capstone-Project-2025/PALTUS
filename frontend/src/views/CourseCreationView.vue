<script setup>
import Logo from "@/components/shared/Logo.vue";
import MyCourses from "@/components/shared/MyCourses.vue";
import Account from "@/components/shared/Account.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import {onMounted, ref} from "vue";
import router from "@/router/index.js";
import { useRoute } from 'vue-router';
import axios from "@/plugins/axios.js"

const route = useRoute();
const name = ref(route.query.courseName || '');
const currentSkills = ref('');
const goal = ref('');
const lessonsNum = ref('');
const duration = ref('');
const courses = ref([]);
const waiting = ref(false);

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
        console.log(response);
        try {
          axios.post('courses/saveCourse', response)
              .then((router.push('/')));
        } catch (error) {
          console.log(error);
        }
      });
    } catch (error) {
      console.log(error);
    }
  } else {
    return 0;
  }
}
</script>

<template>
  <div class="main">
    <section class="left">
      <Logo />
      <MyCourses :courses="courses"/>
    </section>
    <section class="center">
      <div
          class="question"
          v-for="inp in inputs"
      >
        <BaseHeader :text="inp.question" class="header"/>
        <BaseInput :placeholder="inp.placeholder" v-model="inp.model"/>
      </div>
      <div class="question" style="margin-top: 1vh">
        <BaseInput placeholder="Lesson duration (in minutes)" v-model="duration"/>
      </div>
      <ButtonGreen
          v-if="validation() && !waiting"
          title="GET A COURSE"
          class="increased-size"
          @click="getCourse"
      />
      <ButtonGreen
          v-if="!validation() && !waiting"
          title="GET A COURSE"
          class="inactive"
      />
      <BaseHeader v-if="waiting" text="Waiting for server response..." class="waiting" />
    </section>
    <section class="right">
      <Account />
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
  margin-top: 2.5vh;
  padding: 2vh 3vw;
}

.inactive {
  margin-top: 2.5vh;
  padding: 2vh 3vw;
  background-color: #BBDEFB;
  color: #0D47A1;
  cursor: not-allowed;
}

.waiting {
  margin-top: 2.5vh;
}
</style>