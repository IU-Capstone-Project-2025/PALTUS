import { defineStore } from 'pinia';
import axios from "@/plugins/axios.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: 'null',
        token: null,
        expiresIn: null,
        email: '',
        isVerified: false,
    }),
    actions: {
        setUserData(email, username) {
            this.email = email;
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
                this.expiresIn = Number.parseInt(response.expiresIn);

                localStorage.setItem('user', this.user);
                localStorage.setItem('token', this.token);

                this.setLogoutTimer(expiresIn);
            } catch (error) {
                console.error(error);
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
            const expires = localStorage.getItem('expiresIn');

            if (saved && token && expires) {
                const now = Date.now();
                if (now < Number.parseInt(expires)) {
                    this.user = saved;
                    this.token = token;
                    this.expiresIn = expires;
                    this.setLogoutTimer(Number.parseInt(expires) - now); // разница
                } else {
                    this.logout();
                }
            }
        },
        isAuthenticated() {
            return !!this.token;
        }
    },
});
