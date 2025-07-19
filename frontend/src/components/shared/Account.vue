<script setup>
import AccountLink from "@/components/shared/AccountLink.vue";
import {accountLinks} from "@/constants/accountLinks.js";
import ProfileTab from "@/components/shared/ProfileTab.vue";
import {onMounted, ref} from "vue";
import {useAuthStore} from "@/stores/auth.js";
import router from "@/router/index.js";

let showLinks = ref(true);
const auth = useAuthStore();

const logout_func = () => {
  auth.logout();
  router.go();
}

const hideLinks = () => {
  const links = document.querySelectorAll("li");
  links.forEach((link, index) => {
    if (showLinks) {
      link.style.top = `${(index + 1) * -10}vh`
    } else {
      link.style.top = '0';
    }
  })
  showLinks = !showLinks;
}

onMounted(() => {
  hideLinks();
})
</script>

<template>
  <ProfileTab class="profileTab" @click="hideLinks"/>
  <ul>
    <li v-for="accountLink in accountLinks">
      <AccountLink
          v-if="accountLink.link"
          :link="accountLink.link"
          :title="accountLink.title"
      />
      <AccountLink
          v-else
          :isFunc="true"
          :title="accountLink.title"
          @logout="logout_func"
      />
    </li>
  </ul>
</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  position: relative;
  transition: 300ms;
  top: 0;
}

.profileTab {
  z-index: 2;
}
</style>