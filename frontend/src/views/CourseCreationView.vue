<script setup>
import Logo from "@/components/shared/Logo.vue";
import MyCourses from "@/components/shared/MyCourses.vue";
import Account from "@/components/home/Account.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import {ref} from "vue";
import ButtonGreen from "@/components/shared/ButtonGreen.vue";

const props = defineProps({
  courseName: String,
})

const name = ref('');
const currentSkills = ref('');
const goal = ref('');
const lessonsNum = ref('');
const duration = ref('');

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
    ]);
</script>

<template>
  <div class="main">
    <section class="left">
      <Logo />
      <MyCourses />
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
      <ButtonGreen title="GET A COURSE" class="increased-size" />
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
</style>