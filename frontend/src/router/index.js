import { createWebHistory, createRouter } from 'vue-router'
import {useAuthStore} from "@/stores/auth.js";
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import CourseCreationView from "@/views/CourseCreationView.vue";
import AccountView from "@/views/AccountView.vue";
import SettingsView from "@/views/SettingsView.vue";
import CourseView from "@/views/CourseView.vue";
import LevelView from "@/views/LevelView.vue";
import SignInView from "@/views/SignInView.vue";
import AwardsView from "@/views/AwardsView.vue";

const routes = [
    { path: '/login', component: LoginView },
    { path: '/sign_in', component: SignInView},
    {
        path: '/',
        component: HomeView,
        meta: { requiresAuth: true }
    },
    {
        path: '/create_course',
        component: CourseCreationView,
        meta: { requiresAuth: true },
    },
    {
        path: '/account',
        component: AccountView,
        meta: { requiresAuth: true }
    },
    {
        path: '/settings',
        component: SettingsView,
        meta: { requiresAuth: true }
    },
    {
        path: '/level',
        component: LevelView,
        meta: { requiresAuth: true }
    },
    {
        path: '/course/:id',
        component: CourseView,
        props: true,
        meta: { requiresAuth: true }
    },
    {
        path: '/awards',
        component: AwardsView,
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