<script setup>
import {onMounted, ref} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import axios from "@/plugins/axios.js";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";

const ver_code = ref('');
const auth = useAuthStore();
const router = useRouter();
const submitted = ref(false);
const error_message = ref('');
const isError = ref(false);

const validateCode = () => {
  return /^\d{6}$/.test(ver_code.value);
}

const resendCode = async () => {
  try {
    const response = await axios.post('/resend', auth.email);
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}

const checkCode = async () => {
  submitted.value = true;
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
      await auth.login(auth.email, auth.password);
      router.push('/');
    } catch (e) {
      console.error(e);
    }
    await router.push('/');
  } catch (error) {
    if (error.response.status === 500) {
      error_message.value = 'Code is not correct';
      isError.value = true;
    }
    submitted.value = false;
    ver_code.value = '';
  }
}

onMounted(() => {
  if (!auth.email) {
    router.push('/sign_up');
  } else if (auth.isVerified) {
    router.push('/');
  }
  // setTimeout(() => {
  //   document.getElementById('send-again').style.display = 'block';
  // }, 15000)
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
      <ErrorNotification :error_message="error_message" v-if="isError" />
      <ButtonGreen type="submit" title="Submit" v-if="validateCode() && !submitted" />
      <ButtonGreen title="Submit" class="inactive" v-else />
      <p id="send-again" @click="resendCode">I did not receive the code</p>
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

#send-again {
  font-size: 1rem;
  color: #BBDEFB;
  margin-top: 5vh;
  margin-bottom: 1vh;
  cursor: pointer;
  text-decoration: underline;
  display: none;
}
#send-again:hover {
  color: #F5F7FA;
}

.inactive {
  background-color: #BBDEFB;
  color: #0D47A1;
  cursor: not-allowed;
}
</style>
