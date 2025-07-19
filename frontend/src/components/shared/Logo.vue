<script setup>
import router from "@/router/index.js";

const goHome = () => {
  router.push("/");
}
</script>

<template>
  <div class="logo">
    <a @click="goHome">
      <h1>PALTUS</h1>
    </a>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 800;
  font-size: 2.25rem;
}

a {
  text-decoration: none;
  color: inherit;
  cursor: pointer;
}

a:hover {
  text-decoration: none;
}

.logo {
  box-sizing: border-box;
  width: 15vw;
  height: 13vh;
  align-content: center;
  text-align: center;
  color: #42A5F5;
}
</style>