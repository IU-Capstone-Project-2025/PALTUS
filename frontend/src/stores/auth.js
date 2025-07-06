import { defineStore } from 'pinia';
import axios from "@/plugins/axios.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: 'null',
        token: null,
        expiresIn: null,
        email: '',
        password: '',
        isVerified: false,
    }),
    actions: {
        setUserData(email, password, username) {
            this.email = email;
            this.password = password;
            this.user = username;
        },
        async login(email, password, username) {
            const login_data = {
                email: email,
                password: password,
            }
            try {
                const response = await axios.post('/login', login_data);
                console.log(response);
                this.user = username;
                this.email = email;
                this.token = response.token;
                this.expiresIn = response.expiresIn;
                localStorage.setItem('user', this.user);
                localStorage.setItem('token', this.token);
            } catch (error) {
                console.error(error);
            }
        },
        logout() {
            this.user = null;
            this.token = null;
            localStorage.removeItem('user');
            localStorage.removeItem('token');
        },
        loadUser() {
            const saved = localStorage.getItem('user');
            const token = localStorage.getItem('token');
            if (saved && token) {
                this.user = saved;
                this.token = token;
            }
        },
        isAuthenticated() {
            return !!this.token;
        }
    },
});
