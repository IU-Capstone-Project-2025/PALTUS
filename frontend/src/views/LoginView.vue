<script setup>
/**
 * LoginView.vue - login page,
 * used to log in to the app
 */
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import {useAuthStore} from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import BaseButton from "@/components/shared/BaseButton.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";

const email = ref('');
const password = ref('');
const auth = useAuthStore();
const router = useRouter();
const submitted = ref(false);
const error = ref(false);
const error_message = ref('');

async function loginUser() {
  submitted.value = true;
  try {
    await auth.login(email.value, password.value, null);
    await router.push('/');
  } catch (err) {
    if (err.statusCode === 500) {
      error_message.value = 'Wrong password';
      error.value = true;
    } else if (err.statusCode === 404) {
      error_message.value = 'Account does not exist';
      error.value = true;
      email.value = '';
    }
    password.value = '';
    submitted.value = false;
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

onMounted(() => {
  if (auth.isAuthenticated()) {
    router.push('/');
  }
})
</script>

<template>
  <div class="container">
    <Logo/>
    <form @submit.prevent="loginUser">
      <h3>Log In</h3>
      <BaseInput
          v-model="email"
          class="custom-input"
          placeholder="Email"
      />

      <BaseInput
          v-model="password"
          class="custom-input"
          placeholder="Password"
          type="password"
      />
      <ErrorNotification v-if="error" :error_message="error_message"/>

      <BaseButton
          v-if="validation() && !submitted"
          id="submit-button"
          color="green"
          title="Log In"
          type="submit"
      />
      <BaseButton v-else color="inactive" title="Log In"/>
      <p class="register-suggest">Don't have an account?</p>
      <router-link to="/sign_up">
        <BaseButton color="green" title="Sign Up"/>
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
  min-height: 5vh;
  font-size: 1rem;
  margin-bottom: 3vh;
}

.register-suggest {
  font-size: 1rem;
  color: #F5F7FA;
  margin-top: 5vh;
  margin-bottom: 1vh;
}
</style>
