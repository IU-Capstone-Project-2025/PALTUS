import { defineStore } from "pinia";
import axios from "@/plugins/axios.js";

export const useCourseStore = defineStore("course", {
    state: () => ({
        course_name: '',
        description: '',
        books: [],
        lessons: [],
    }),
    actions: {
        async loadCourse(id) {
            try {
                const response = await axios.get(`courses/${id}`);
                console.log(response);
                this.course_name = response.course_name;
                this.description = response.description;
                this.books = response.books;
                this.lessons = response.lessons;
            } catch (error) {
                console.log(error);
            }
        }
    }
})