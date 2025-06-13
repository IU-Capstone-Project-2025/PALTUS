<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const email = ref('');
const password = ref('');
const auth = useAuthStore();
const router = useRouter();

async function loginUser() {
  try {
    await auth.login(email.value, password.value);
    await router.push('/');
  } catch (err) {
    alert(err.message);
  }
}
</script>

<template>
  <form @submit.prevent="loginUser">
    <input v-model="email" placeholder="Email" />
    <!--    test@example.com-->
    <input v-model="password" type="password" placeholder="Password" />
    <!--    123-->
    <button type="submit">Log In</button>
  </form>
</template>
