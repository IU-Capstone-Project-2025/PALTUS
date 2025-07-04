import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: 'null',
        token: null,
        email: '',
        password: '',
        verificationCode: null,
    }),
    actions: {
        setUserData(email, password, username) {
            this.email = email;
            this.password = password;
            this.user = username;
        },
        login(email, password) {
            // Mock data
            if (email === 'test@example.com' && password === '123') {
                this.user = { email };
                this.token = 'mock-token';
                localStorage.setItem('user', JSON.stringify(this.user));
                localStorage.setItem('token', this.token);
            } else {
                throw new Error('email - test@example.com\n' +
                                'password - 123');
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
                this.user = JSON.parse(saved);
                this.token = token;
            }
        },
        isAuthenticated() {
            return !!this.token;
        }
    },
});
