<script setup>
import {computed, onMounted, ref} from 'vue';
import {useAuthStore} from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import BaseButton from "@/components/shared/BaseButton.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import router from "@/router/index.js";
import axios from "@/plugins/axios.js";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";

const email = ref('');
const password = ref('');
const name = ref('');
const auth = useAuthStore();
const submitted = ref(false);
const error = ref(false);
const error_message = ref('')


const isValidEmail = computed(() => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email.value);
});

const checkFields = () => {
  return !!(isValidEmail.value && password.value.length && name.value.length);
}

const signUp = async () => {
  submitted.value = true;
  try {
    const userData = {
      username: name.value,
      email: email.value,
      password: password.value,
    }
    const response = await axios.post('/register', userData);
    console.log(response);
    auth.setUserData(email.value, name.value, password.value);
    router.push('/verify')
  } catch (err) {
    if (err.response.status === 500) {
      error_message.value = 'Account with this email already exists';
      email.value = '';
    }
    error.value = true;
    submitted.value = false;
  }
}

onMounted(() => {
  if (auth.isAuthenticated()) {
    router.push('/');
  }
})
</script>

<template>
  <div class="container">
    <Logo/>
    <form @submit.prevent="signUp">
      <h3>Sign Up</h3>
      <BaseInput
          v-model="email"
          class="custom-input"
          placeholder="Email"
      />

      <BaseInput
          v-model="name"
          class="custom-input"
          placeholder="Your Name"
      />

      <BaseInput
          v-model="password"
          class="custom-input"
          placeholder="Password"
          type="password"
      />
      <ErrorNotification v-if="error" :error_message="error_message"/>
      <BaseButton v-if="checkFields() && !submitted" color="green" title="Sign Up" type="submit"/>
      <BaseButton v-else color="inactive" title="Sign Up"/>
    </form>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h3 {
  font-size: 1.75rem;
  font-weight: 700;
  color: #F5F7FA;
  text-align: center;
  margin-bottom: 5vh;
}

form {
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  align-items: center;
  background-color: #42A5F5;
  padding: 3vh 1vw;
  margin-top: 10vh;
  border-radius: 16px;
}

.custom-input {
  height: 5vh;
  min-height: 5vh;
  font-size: 1rem;
  margin-bottom: 3vh;
}
</style>
