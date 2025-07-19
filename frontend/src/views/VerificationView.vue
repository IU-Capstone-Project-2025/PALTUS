<script setup>
/**
 * Verification.vue - verification page,
 * redirects to the Home page if form is correct
 */
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import {useAuthStore} from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import BaseButton from "@/components/shared/BaseButton.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import axios from "@/plugins/axios.js";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";

const ver_code = ref('');
const auth = useAuthStore();
const router = useRouter();
const submitted = ref(false);
const error_message = ref('');
const isError = ref(false);
const resent = ref(false);
const email = auth.email;
const password = auth.password;
const user = auth.user;

const validateCode = () => {
  return /^\d{6}$/.test(ver_code.value);
}

const resendCode = async () => {
  try {
    await axios.post('/resend', email, {
      headers: {"Content-Type": "text/plain"}
    });
    resent.value = true;
  } catch (error) {
    console.err(error);
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
      await auth.login(email, password, user);
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
  if (auth.isAuthenticated()) {
    router.push('/');
  } else if (!auth.email) {
    router.push('/sign_up');
  }

  setTimeout(() => {
    document.getElementById('send-again').style.display = 'block';
  }, 15000)
})
</script>

<template>
  <div class="container">
    <Logo/>
    <form @submit.prevent="checkCode">
      <h3>Check your email</h3>
      <BaseInput
          v-model="ver_code"
          class="custom-input"
          placeholder="Enter your verification code"
      />
      <ErrorNotification v-if="isError" :error_message="error_message"/>
      <BaseButton v-if="validateCode() && !submitted" color="green" title="Submit" type="submit"/>
      <BaseButton v-else color="inactive" title="Submit"/>
      <p v-if="!resent" id="send-again" @click="resendCode">I did not receive the code</p>
      <p v-if="resent" class="resent">Code was resent, check your email</p>
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

.resent {
  font-size: 1rem;
  margin-top: 5vh;
  margin-bottom: 1vh;
  color: #F5F7FA;
}
</style>
