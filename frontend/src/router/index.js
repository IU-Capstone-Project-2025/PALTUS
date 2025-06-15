import { createWebHistory, createRouter } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import {useAuthStore} from "@/stores/auth.js";
import CourseCreationView from "@/views/CourseCreationView.vue";

const routes = [
    { path: '/login', component: LoginView },
    {
        path: '/',
        component: HomeView,
        meta: { requiresAuth: true }
    },
    {
        path: '/create_course',
        component: CourseCreationView,
        props: true,
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const auth = useAuthStore();
    auth.loadUser();

    if (to.meta.requiresAuth && !auth.isAuthenticated()) {
        next('/login');
    } else {
        next();
    }
});

export default router