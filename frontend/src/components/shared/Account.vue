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
    }
    else {
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
  <ProfileTab @click="hideLinks" class="profileTab" />
  <ul>
    <li v-for="accountLink in accountLinks">
      <AccountLink
          :title="accountLink.title"
          :link="accountLink.link"
          v-if="accountLink.link"
      />
      <AccountLink
          :title="accountLink.title"
          :isFunc="true"
          @logout="logout_func"
          v-else
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