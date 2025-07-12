import { createWebHistory, createRouter } from 'vue-router'
import {useAuthStore} from "@/stores/auth.js";
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import CourseCreationView from "@/views/CourseCreationView.vue";
import AccountView from "@/views/AccountView.vue";
import SettingsView from "@/views/SettingsView.vue";
import CourseView from "@/views/CourseView.vue";
import LevelView from "@/views/LevelView.vue";
import SignUpView from "@/views/SignUpView.vue";
import VerificationView from "@/views/VerificationView.vue";

const routes = [
    { path: '/login', component: LoginView },
    { path: '/sign_up', component: SignUpView},
    { path: '/verify', component: VerificationView},
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
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const auth = useAuthStore();

    if (!auth.token) {
        auth.loadUser();
    }

    const isAuth = auth.isAuthenticated();

    if (to.meta.requiresAuth && !isAuth) {
        if (to.path !== '/login') {
            return next('/login');
        }
    }

    if (!!auth.expiresAt && (Date.now() > auth.expiresAt)) {
        auth.logout();
        if (to.path !== '/login') {
            return next('/login');
        } else {
            return next();
        }
    }

    next();
});


export default router