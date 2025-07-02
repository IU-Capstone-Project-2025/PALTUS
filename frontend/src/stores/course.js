import { defineStore } from "pinia";
import axios from "@/plugins/axios.js";

export const useCourseStore = defineStore("course", {
    state: () => ({
        course_name: '',
        description: '',
        books: [],
        lessons: [],
        courseId: 0
    }),
    actions: {
        async loadCourse(id) {
            try {
                const response = await axios.get(`courses/${id}`);
                this.courseId = response.id;
                this.course_name = response.course_name;
                this.description = response.description;
                this.books = response.books;
                this.lessons = response.lessons;
            } catch (error) {
                console.error(error);
            }
        },
        async updateSubtopic(subtopicChanged, lesson_id){
            try {
                await axios.put(`lessons/${lesson_id}/subtopics/setFinished/${subtopicChanged.id}`, {
                    courseId: this.courseId,
                    state: subtopicChanged.finished
                })
            } catch (error) {
                console.error(error);
            }
        }
    }
})