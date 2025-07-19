<script setup>
/**
 * QuizView.vue - quiz page,
 * available only with authorization, shows quiz content
 */
import {useQuizStore} from "@/stores/quiz.js";
import {onMounted, reactive, ref} from "vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";
import BaseButton from "@/components/shared/BaseButton.vue";
import Timer from "@/components/quiz/Timer.vue";
import router from "@/router/index.js";
import axios from "@/plugins/axios.js";
import {useRoute} from "vue-router";
import QuizContent from "@/components/quiz/QuizContent.vue";

const quiz = useQuizStore();
const answers = reactive([]);
const time = ref(5 * 60);
const timer = ref(null);
const result = ref(0);
const finished = ref(false);
const passed = ref(false);
const lessonId = ref(null);

const startTimer = () => {
  timer.value = setInterval(() => {
    if (time.value > 0) {
      time.value--;
    } else {
      checkAnswers();
      clearInterval(timer.value);
    }
  }, 1000);
};

onMounted(() => {
  lessonId.value = useRoute().params.lessonId
  if (!quiz.quizTitle) {
    router.back();
  }
  startTimer();
})

const validation = () => {
  return answers.length === quiz.questions.length;
}

const chooseAnswer = (index, questionIndex) => {
  if (!finished.value) {
    answers[questionIndex] = index;
  }
}

const sendBack = () => {
  router.back();
}

const checkAnswers = async () => {
  const submittedAnswers = answers.filter(answer => answer !== null);
  result.value = quiz.checkAnswers(submittedAnswers);
  finished.value = true;
  clearInterval(timer.value);
  if (result.value > 0.5) {
    passed.value = true;
    await axios.put(`lessons/passQuiz/${lessonId.value}`);
  }
}

const isAnswerWrong = (questionIndex) => {
  return finished.value && answers[questionIndex] !== quiz.correctAnswers[questionIndex];
}
</script>

<template>
  <ErrorNotification
      v-if="!quiz.quizTitle"
      class="error"
      error_message="Quiz was not generated"
  />
  <div v-else class="quiz-view">
    <div class="quiz-header">
      <BaseHeader :text="quiz.quizTitle"/>
    </div>
    <div class="timer-container">
      <Timer
          :time="time"
          class="timer"
      />
    </div>

    <div>
      <QuizContent
          :answers="answers"
          :finished="finished"
          :quiz="quiz"
          @chooseAnswer="chooseAnswer"
          @isAnswerWrong="isAnswerWrong"
      />
      <div v-if="validation() && !finished" class="button-container">
        <BaseButton
            title="Submit"
            type="submit"
            @click="checkAnswers"
        />
      </div>
      <div v-else-if="!validation() && !finished" class="button-container">
        <BaseButton color="inactive" title="Submit"/>
      </div>
      <div v-if="passed && finished" class="passed-result">
        Your result is {{ Math.round(result * 100) }}%. You passed the quiz!
      </div>
      <div v-else-if="!passed && finished" class="not-passed-result">
        Your result is {{ Math.round(result * 100) }}%. You have not passed the quiz
      </div>
      <div v-if="finished" class="button-container">
        <BaseButton
            title="Back to the course"
            @click="sendBack"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.quiz-view {
  width: 60vw;
  margin: 2vh auto;
  padding: 2vh;
  color: #0D47A1;
}

.error {
  text-align: center;
  font-size: 2rem;
  height: 100%;
  margin-top: 30vh;
}

ul {
  list-style-type: none;
}

.quiz-header {
  text-align: center;
  margin-bottom: 3vh;
}

.timer-container {
  position: sticky;
  top: 0;
  z-index: 100;
  width: 100%;
}

.timer {
  width: 100%;
  display: flex;
  justify-content: center;
  font-weight: 500;
  color: #42A5F5;
  background-color: #E3F2FD;
  font-size: 3rem;
}

.passed-result {
  width: 100%;
  margin: 3rem 0;
  text-align: center;
  font-weight: 500;
  color: #1B5E20;
  font-size: 2rem;
}

.not-passed-result {
  width: 100%;
  margin: 3rem 0;
  text-align: center;
  font-weight: 500;
  color: #ed1919;
  font-size: 2rem;
}

.button-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 2vh 0;
}
</style>