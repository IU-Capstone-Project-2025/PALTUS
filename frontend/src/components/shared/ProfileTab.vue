<script setup>
import {useAuthStore} from "@/stores/auth.js";
</script>

<template>
  <a>
    <div class="profile">
      {{ useAuthStore().user }}
    </div>
  </a>
</template>

<style scoped>
.profile {
  box-sizing: border-box;
  width: 23vw;
  height: 10vh;
  font-size: 1.125rem;
  padding-left: 5vw;
  background-color: #0D47A1;
  align-content: center;
  color: #F5F7FA;
  font-weight: bold;
  border-bottom-left-radius: 16px;
}

a {
  cursor: pointer;
}
</style>