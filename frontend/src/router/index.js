import { createMemoryHistory, createRouter } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import {useAuthStore} from "@/stores/auth.js";

const routes = [
    { path: '/login', component: LoginView },
    {
        path: '/',
        component: HomeView,
        meta: { requiresAuth: true }
    },
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const auth = useAuthStore();
    auth.loadUser(); // загружаем из localStorage

    if (to.meta.requiresAuth && !auth.isAuthenticated()) {
        next('/login');
    } else {
        next();
    }
});

export default router