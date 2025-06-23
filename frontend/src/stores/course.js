import { defineStore } from "pinia";
import axios from "axios";

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
                const response = await axios.get(`/api/courses/${id}`);
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