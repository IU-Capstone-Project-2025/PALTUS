<script setup>
import {useAuthStore} from "@/stores/auth.js";
import AccountLink from "@/components/home/AccountLink.vue";
import {accountLinks} from "@/constants/accountLinks.js";
</script>

<template>
  <div class="profile">
    {{ useAuthStore().user.email }}
  </div>
  <div class="options">
    <ul>
      <li v-for="accountLink in accountLinks">
        <AccountLink :title="accountLink.title" :link="accountLink.link" />
      </li>
    </ul>
  </div>
</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

.profile {
  box-sizing: border-box;
  width: 23vw;
  height: 10vh;
  font-size: 18px;
  padding-left: 5vw;
  background-color: #0D47A1;
  align-content: center;
  color: #F5F7FA;
  font-weight: bold;
  border-bottom-left-radius: 16px;
}
</style>