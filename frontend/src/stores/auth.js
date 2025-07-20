/**
 * auth.js - store for authorization
 * Used for log in, log out and check authentication
 */
import {defineStore} from 'pinia';
import axios from "@/plugins/axios.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: '',
        password: '',
        token: null,
        expiresAt: null,
        email: '',
    }),
    actions: {
        setUserData(email, username, password) {
            this.email = email;
            this.user = username;
            this.password = password;
        },
        async login(email, password) {
            const login_data = {
                email: email,
                password: password,
            }
            try {
                const response = await axios.post('/login', login_data);
                console.log(response);
                this.user = response.userName;
                this.password = '';

                if (!this.user) {
                    this.user = email.split('@')[0];
                }
                this.token = response.token;
                const expiresIn = Number.parseInt(response.expiresIn);
                this.expiresAt = Date.now() + expiresIn

                localStorage.setItem('user', this.user);
                localStorage.setItem('token', this.token);
                localStorage.setItem('expiresAt', this.expiresAt)
            } catch (error) {
                console.error(error);
                throw {
                    statusCode: error?.response?.status,
                }
            }
        },
        logout() {
            this.user = null;
            this.token = null;
            this.expiresAt = null;
            localStorage.removeItem('user');
            localStorage.removeItem('token');
            localStorage.removeItem('expiresAt')
        },
        loadUser() {
            const saved = localStorage.getItem('user');
            const token = localStorage.getItem('token');
            const expiresAt = Number(localStorage.getItem('expiresAt'));

            if (saved && token) {
                this.user = saved;
                this.token = token;
                this.expiresAt = expiresAt;
            } else {
                this.logout();
            }
        },
        isAuthenticated() {
            return !!this.token;
        }
    },
});
