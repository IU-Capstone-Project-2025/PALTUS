<script setup>
import {useQuizStore} from "@/stores/quiz.js";
import {onMounted, reactive, ref} from "vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";
import ButtonDefault from "@/components/shared/ButtonDefault.vue";

const quiz = useQuizStore();
const answers = reactive([]);
const time = ref(5*60);
const timer = ref(null);

const formatTime = (seconds) => {
  const mins = Math.floor(seconds / 60);
  const secs = seconds % 60;
  return `${mins}:${secs < 10 ? '0' : ''}${secs}`;
};

const startTimer = () => {
  timer.value = setInterval(() => {
    if (time.value > 0) {
      time.value--;
    } else {
      clearInterval(timer.value);
    }
  }, 1000);
};

onMounted(() => {
  console.log(quiz);
  startTimer();
})

const chooseAnswer = (index, questionIndex) => {
  answers[questionIndex] = index;
}
</script>

<template>
  <ErrorNotification
      error_message="Quiz was not generated"
      class="error"
      v-if="!quiz.quizTitle"
  />
  <div class="quiz-view" v-else>
    <div class="quiz-header">
      <BaseHeader :text="quiz.quizTitle" />
    </div>
    <div class="timer-container">
      <div class="timer">
        {{ formatTime(time) }}
      </div>
    </div>

    <div>
      <ul class="quiz-content" >
        <li v-for="(question, questionIndex) in quiz.questions" class="question-container">
          <h3 class="question-title">Question {{ questionIndex + 1 }}</h3>
          <p class="question-text">{{ question.questionText }}</p>

          <ul>
            <li class="options-container" v-for="(option, index) in question.options">
              <div class="option">
                <input
                    type="radio"
                    :id="option"
                    :value="option"
                    @click="chooseAnswer(index, questionIndex)"
                >
                <label :for="option">{{ option }}</label>
              </div>
            </li>
          </ul>
        </li>
      </ul>
      <div class="button-container">
        <ButtonDefault title="Submit" />
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

.question-container {
  background: #F5F7FA;
  border-radius: 12px;
  padding: 25px;
  margin-bottom: 2vh;
}

.question-title {
  font-size: 1.3rem;
  font-weight: bold;
  margin: 2vh 0;
  color: #42A5F5;
}

.question-text {
  font-size: 1.1rem;
  margin-bottom: 2vh;
  line-height: 1.5;
  font-weight: bold;
}

.options-container {
  display: flex;
  flex-direction: column;
  gap: 3vh;
}

.option {
  display: flex;
  align-items: center;
  margin: 1vh 0;
}

.option input[type="radio"] {
  appearance: none;
  width: 20px;
  height: 20px;
  border: 2px solid #42A5F5;
  border-radius: 50%;
  margin-right: 15px;
  cursor: pointer;
  position: relative;
}

.option input[type="radio"]:checked {
  background-color: #42A5F5;
}

.option input[type="radio"]:checked::after {
  content: '';
  position: absolute;
  width: 10px;
  height: 10px;
  background: white;
  border-radius: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.option label {
  font-size: 1rem;
  cursor: pointer;
  color: #0D47A1;
  font-weight: 300;
  flex: 1;
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

.button-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 2vh 0;
}
</style>