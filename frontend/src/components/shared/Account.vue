<script setup>
import {useAuthStore} from "@/stores/auth.js";
import AccountLink from "@/components/shared/AccountLink.vue";
import {accountLinks} from "@/constants/accountLinks.js";
import ProfileTab from "@/components/shared/ProfileTab.vue";
</script>

<template>
  <ProfileTab />
  <ul>
    <li v-for="accountLink in accountLinks">
      <AccountLink :title="accountLink.title" :link="accountLink.link" />
    </li>
  </ul>
</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}
</style>