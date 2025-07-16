import { defineStore } from 'pinia';
import axios from "@/plugins/axios.js";
import router from "@/router/index.js";

export const useQuizStore = defineStore('quiz', {
    state: () => ({
        quizTitle: '',
        questions: [],
        correctAnswers: [],
        userAnswers: [],
    }),
    actions: {
        async loadQuiz(lessonId) {
            try {
                const response = await axios.get(`/quiz/${lessonId}`);
                console.log(response);
                this.quizTitle = response.quizTitle;
                const newQuestions = [];
                const correct = [];
                for (const question in response.questions) {
                    const newQuestion = {};
                    newQuestion.questionId = question.questionId
                    newQuestion.questionText = question.questionText;
                    newQuestion.options = question.options;
                    correct.push(question.correctAnswer)
                    newQuestions.push(newQuestion);
                }
                this.questions = newQuestions;
                this.correctAnswers = correct;
                await router.push('/quiz');
            } catch (err) {
                console.log(err);
            }
        },
        checkAnswers(answer) {

        },
    },
});
