<script setup>
defineProps({
  quiz: {
    type: Object,
    required: true
  },
  finished: {
    type: Boolean,
    required: true
  },
  answers: {
    type: Array,
    required: true
  }
})

defineEmits(['chooseAnswer', 'isAnswerWrong'])
</script>

<template>
  <ul class="quiz-content" >
    <li v-for="(question, questionIndex) in quiz.questions" class="question-container">
      <h3 class="question-title">Question {{ questionIndex + 1 }}</h3>
      <p class="question-text">{{ question.questionText }}</p>

      <ul>
        <li class="options-container" v-for="(option, index) in question.options">
          <div class="option">
            <input
                type="radio"
                :name="'question_' + questionIndex"
                :id="'option_' + questionIndex + '_' + index"
                :value="index"
                @click="$emit('chooseAnswer', index, questionIndex)"
                :checked="answers[questionIndex] === index"
                :disabled="finished"
            >
            <label :for="'option_' + questionIndex + '_' + index">{{ option }}</label>
          </div>
        </li>
      </ul>
      <div
          class="correct-answer"
          :class="{ 'wrong-answer': $emit('isAnswerWrong', questionIndex) }"
          v-if="finished"
      >
        Correct answer: {{ quiz.questions[questionIndex].options[quiz.correctAnswers[questionIndex]] }}
      </div>
    </li>
  </ul>
</template>

<style scoped>
ul {
  list-style-type: none;
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

.option input[type="radio"]:disabled {
  cursor: not-allowed;
  opacity: 0.7;
}

.option label {
  font-size: 1rem;
  cursor: pointer;
  color: #0D47A1;
  font-weight: 300;
  flex: 1;
}

.correct-answer {
  font-size: 0.9rem;
  color: #48CFAD;
  margin-left: 4vw;
}

.wrong-answer {
  color: #ed1919;
}
</style>