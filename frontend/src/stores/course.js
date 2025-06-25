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
                console.log(response);
                this.courseId = response.id;
                this.course_name = response.course_name;
                this.description = response.description;
                this.books = response.books;
                this.lessons = response.lessons;
            } catch (error) {
                console.error(error);
            }
        },
        async updateSubtopic(subtopicChanged){
            try {
                await axios.put(`api/v1/lessons/subtopic/${subtopicChanged.id}`, {
                    courseId: this.courseId,
                    state: subtopicChanged.finished
                })
            } catch (error) {
                console.error(error);
            }
        }
    }
})