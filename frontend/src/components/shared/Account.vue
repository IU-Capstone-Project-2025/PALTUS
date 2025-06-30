<script setup>
import AccountLink from "@/components/shared/AccountLink.vue";
import {accountLinks} from "@/constants/accountLinks.js";
import ProfileTab from "@/components/shared/ProfileTab.vue";
import {ref} from "vue";

let showLinks = ref(true);
</script>

<template>
  <ProfileTab @click="showLinks = !showLinks" />
  <ul>
    <li v-for="accountLink in accountLinks" v-if="showLinks">
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