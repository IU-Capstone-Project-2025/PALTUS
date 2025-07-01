<script setup>
import { ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import Logo from '../components/shared/Logo.vue'
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseInput from "@/components/shared/BaseInput.vue";

const email = ref('');
const password = ref('');
const name = ref('');
const auth = useAuthStore();

const signIn = async () => {

}
</script>

<template>
  <div class="container">
    <Logo />
    <form @submit.prevent="signIn">
      <h3>Sign In</h3>
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

      <ButtonGreen type="submit" title="Sign In" />
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
</style>
