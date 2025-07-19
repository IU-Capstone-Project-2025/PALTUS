import {defineStore} from 'pinia';
import axios from "@/plugins/axios.js";
import router from "@/router/index.js";

export const useQuizStore = defineStore('quiz', {
    state: () => ({
        quizTitle: '',
        questions: [],
        correctAnswers: []
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
                await router.push(`/quiz/${lessonId}`);
            } catch (err) {
                console.log(err);
            }
        },
        checkAnswers(answers) {
            let correctCount = 0;
            for (let i = 0; i < answers.length; i++) {
                if (this.correctAnswers[i] === answers[i]) {
                    correctCount++;
                }
            }
            console.log(correctCount);
            return answers.length > 0 ? correctCount / this.questions.length : 0;
        }
    },
});
