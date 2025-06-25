import axios from "axios";

const instance = axios.create({
    baseURL: '/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

// instance.interceptors.request.use(config => {
//     const token = localStorage.getItem('token');
//     if (token) {
//         config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
// }, error => Promise.reject(error));

instance.interceptors.response.use(response => {
    return response.data;
}, error => {
    console.log('Axios Error: ', error);
    return Promise.reject(error);
})

export default instance;