<script setup>
import {onMounted, ref} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import axios from "@/plugins/axios.js";

const ver_code = ref('');
const auth = useAuthStore();
const router = useRouter();

const validateCode = () => {
  return /^\d{6}$/.test(ver_code.value);
}

const checkCode = async () => {
  const ver_data = {
    email: auth.email,
    verificationCode: ver_code.value,
  };
  try {
    const response = await axios.post('/verify', ver_data);
    console.log(response);
    auth.isVerified = true;
    auth.token = response.token;
    try {
      await auth.login(auth.email, auth.password, auth.user);
      router.push('/');
    } catch (e) {
      console.error(e);
    }
    await router.push('/');
  } catch (error) {
    console.log(error);
  }
}

onMounted(() => {
  if (!auth.email) {
    router.push('/sign_up');
  } else if (auth.isVerified) {
    router.push('/');
  }
})
</script>

<template>
  <div class="container">
    <Logo />
    <form @submit.prevent="checkCode">
      <h3>Check your email</h3>
      <BaseInput
          v-model="ver_code"
          placeholder="Enter your verification code"
          class="custom-input"
      />

      <ButtonGreen type="submit" title="Submit" v-if="validateCode()" />
      <ButtonGreen title="Submit" class="inactive" v-else />
      <p>I did not receive the code</p>
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
  height: 8vh;
  font-size: 1.2rem;
  margin-bottom: 3vh;
  text-align: center;
}

p {
  font-size: 1rem;
  color: #BBDEFB;
  margin-top: 5vh;
  margin-bottom: 1vh;
  cursor: pointer;
  text-decoration: underline;
}
p:hover {
  color: #F5F7FA;
}

.inactive {
  background-color: #BBDEFB;
  color: #0D47A1;
  cursor: not-allowed;
}
</style>
