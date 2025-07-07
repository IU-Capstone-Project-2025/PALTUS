<script setup>
import {computed, ref} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseInput from "@/components/shared/BaseInput.vue";

const email = ref('');
const password = ref('');
const auth = useAuthStore();
const router = useRouter();
const submitted = ref(false);

async function loginUser() {
  submitted.value = true;
  console.log("Trying login with:", email.value, password.value);
  try {
    await auth.login(email.value, password.value, email.value);
    await router.push('/');
  } catch (err) {
    alert(err.message);
  }
}

const isValidEmail = computed(() => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email.value);
});

const isValidPassword = computed(() => {
  return !!password.value;
});

const validation = () => {
  return isValidPassword.value && isValidEmail.value;
}
</script>

<template>
  <div class="container">
    <Logo />
    <form @submit.prevent="loginUser">
      <h3>Log In</h3>
      <BaseInput
          v-model="email"
          placeholder="Email"
          class="custom-input"
      />

      <BaseInput
          v-model="password"
          placeholder="Password"
          type="password"
          class="custom-input"
      />

      <ButtonGreen
          type="submit"
          title="Log In"
          v-if="validation() && !submitted"
          id="submit-button"
      />
      <ButtonGreen title="Log In" class="inactive" v-else />
      <p>Don't have an account?</p>
      <router-link to="/sign_up">
        <ButtonGreen title="Sign Up" />
      </router-link>
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
