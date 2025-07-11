import { defineStore } from 'pinia';
import axios from "@/plugins/axios.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: '',
        password: '',
        token: null,
        expiresIn: null,
        expiresAt: null,
        email: '',
        isVerified: false,
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
            console.log(`${this.user} trying to log in`)
            try {
                const response = await axios.post('/login', login_data);
                console.log(response);

                if (!this.user) {
                    this.user = email.split('@')[0];
                }
                this.password = '';
                this.email = email;
                this.token = response.token;
                this.expiresIn = Number.parseInt(response.expiresIn);

                localStorage.setItem('user', this.user);
                localStorage.setItem('token', this.token);

                this.expiresAt = Date.now() + this.expiresIn
                this.setLogoutTimer(this.expiresIn);
            } catch (error) {
                console.error(error);
                throw {
                    statusCode: error?.response?.status,
                }
            }
        },
        setLogoutTimer(msUntilLogout) {
            setTimeout(() => {
                this.logout();
            }, msUntilLogout);
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
            } else {
                this.logout();
            }
        },
        isAuthenticated() {
            return !!this.token;
        }
    },
});
