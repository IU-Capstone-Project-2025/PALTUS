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
                const quizData = await axios.get(`/quiz/${lessonId}`);
                console.log(quizData);
                this.quizTitle = quizData.quizTitle;
                const newQuestions = [];
                const correct = [];

                for (const question of quizData.questions) {
                    const newQuestion = {
                        questionId: question.questionId,
                        questionText: question.questionText,
                        options: [...question.options]
                    };

                    correct.push(question.correctAnswer);
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
