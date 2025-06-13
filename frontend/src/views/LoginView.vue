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
  <div class="container">
    <div class="logo">
      <h1>PALTUS</h1>
    </div>
    <form @submit.prevent="loginUser">
      <h3>Log In</h3>
      <input v-model="email" placeholder="Email" />
      <!--    test@example.com-->
      <input v-model="password" type="password" placeholder="Password" />
      <!--    123-->
      <button type="submit">Log In</button>
      <p>Don't have an account?</p>
      <button>Sign In</button>
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
.logo {
  width: 15vw;
  height: 13vh;
  justify-items: center;
  align-content: center;
  color: #42A5F5;
}
button {
  background-color: #48CFAD;
  border-style: none;
  border-radius: 20px;
  padding: 10px 30px;
  font-family: Montserrat, Inter, sans-serif;
  color: #F5F7FA;
  font-size: 14px;
  transition: 500ms;
  cursor: pointer;
  width: 10vw;
}
button:hover {
  background-color: #BBDEFB;
  color: #0D47A1;
}
h1 {
  font-weight: 800;
}
h3 {
  font-size: 28px;
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
input {
  font-family: Montserrat, Inter, sans-serif;
  color: #0D47A1;
  border-style: none;
  border-radius: 20px;
  height: 5vh;
  width: 18vw;
  padding-left: 30px;
  padding-right: 10px;
  font-size: 16px;
  margin-bottom: 3vh;
}
textarea:focus, input:focus {
  outline: none;
}
::placeholder {
  color: #abd4f6;
}
p {
  font-size: 16px;
  color: #F5F7FA;
  margin-top: 5vh;
  margin-bottom: 1vh;
}
</style>
