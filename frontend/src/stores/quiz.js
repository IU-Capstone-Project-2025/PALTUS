import { defineStore } from 'pinia';
import axios from "@/plugins/axios.js";

export const useQuizStore = defineStore('quiz', {
    state: () => ({
        quizTitle: '',
        questions: [],
        correctAnswers: [],
        userAnswers: [],
    }),
    actions: {
        async loadQuiz(lessonId) {
            const quiz = axios.get(`/quiz/${lessonId}`);
            this.quizTitle = quiz.title;
            const newQuestions = [];
            const correct = [];
            for (const question in quiz.questions) {
                const newQuestion = {};
                newQuestion.questionId = question.questionId
                newQuestion.questionText = question.questionText;
                newQuestion.options = question.options;
                correct.push(question.correctAnswer)

                newQuestions.push(newQuestion);
            }
            this.questions = newQuestions;
            this.correctAnswers = correct;
        },
        checkAnswers(answer) {

        },
    },
});
