<script setup>
import { ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import router from "@/router/index.js";
import axios from "@/plugins/axios.js";

const email = ref('');
const password = ref('');
const name = ref('');
const auth = useAuthStore();

const checkFields = () => {
  return !!(email.value.length && password.value.length && name.value.length);
}

const signUp = async () => {
  try {
    const userData = {
      username: name.value,
      email: email.value,
      password: password.value,
    }
    const response = await axios.post('/register', userData);

    auth.setUserData(email.value, password.value, name.value);
    router.push('/verify')
  } catch (err) {
    console.log(err);
  }
}
</script>

<template>
  <div class="container">
    <Logo />
    <form @submit.prevent="signUp">
      <h3>Sign Up</h3>
      <BaseInput
          v-model="email"
          placeholder="Email"
          class="custom-input"
      />

      <BaseInput
          v-model="name"
          placeholder="Your Name"
          class="custom-input"
      />

      <BaseInput
          v-model="password"
          placeholder="Password"
          type="password"
          class="custom-input"
      />

      <ButtonGreen v-if="checkFields()" type="submit" title="Sign Up" />
      <ButtonGreen v-else title="Sign Up" class="inactive" />
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
  font-size: 1rem;
  margin-bottom: 3vh;
}

p {
  font-size: 1rem;
  color: #F5F7FA;
  margin-top: 5vh;
  margin-bottom: 1vh;
}

.inactive {
  background-color: #BBDEFB;
  color: #0D47A1;
  cursor: not-allowed;
}
</style>
