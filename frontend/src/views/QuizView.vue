<script setup>
import {useQuizStore} from "@/stores/quiz.js";
import {onBeforeMount, onMounted} from "vue";
import {useRoute} from "vue-router";

const quiz = useQuizStore();
const route = useRoute();


onBeforeMount(() => {
  const lessonId = route.params.lessonId;
  quiz.loadQuiz(lessonId);
})
</script>

<template>
  <div class="quiz-view">
    <div class="quiz-header">
      <h1>QUIZ</h1>
      <h3 class="quiz-topic">Python Basics</h3>
    </div>

    <div class="quiz-content">
      <div class="question-container">
        <h3 class="question-title">Question 1</h3>
        <p class="question-text">What is the output of print(3 + 2 * 2) in Python?</p>

        <div class="options-container">
          <div class="option">
            <input type="radio" id="option-1" name="quiz-option" value="7">
            <label for="option-1">7</label>
          </div>
          <div class="option">
            <input type="radio" id="option-2" name="quiz-option" value="9">
            <label for="option-2">9</label>
          </div>
          <div class="option">
            <input type="radio" id="option-3" name="quiz-option" value="1">
            <label for="option-3">1</label>
          </div>
          <div class="option">
            <input type="radio" id="option-4" name="quiz-option" value="3">
            <label for="option-4">3</label>
          </div>
        </div>
      </div>

      <div class="quiz-footer">
        <div class="timer">
          <span>Time left: 5:00</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.quiz-view {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Montserrat', sans-serif;
  color: #000;
}

.quiz-header {
  text-align: center;
  margin-bottom: 30px;
}

.quiz-header h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #42A5F5;
  margin-bottom: 10px;
}

.quiz-header h2 {
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 5px;
}

.quiz-topic {
  font-size: 1.4rem;
  font-weight: 500;
  color: #42A5F5;
  margin-top: 10px;
}

.question-container {
  background: white;
  border-radius: 12px;
  padding: 25px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.question-title {
  font-size: 1.3rem;
  font-weight: 600;
  margin-bottom: 15px;
  color: #42A5F5;
}

.question-text {
  font-size: 1.1rem;
  margin-bottom: 25px;
  line-height: 1.5;
}

.options-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.option {
  display: flex;
  align-items: center;
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
  flex: 1;
}

.quiz-footer {
  display: flex;
  justify-content: flex-end;
}

.timer {
  background: #f0f0f0;
  padding: 10px 20px;
  border-radius: 20px;
  font-weight: 500;
  color: #555;
}
</style>