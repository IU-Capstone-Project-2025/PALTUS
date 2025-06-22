<script setup>
import Logo from "@/components/shared/Logo.vue";
import MyCourses from "@/components/shared/MyCourses.vue";
import BaseInput from "@/components/shared/BaseInput.vue";
import ContinueStudying from "@/components/home/ContinueStudying.vue";
import Account from "@/components/shared/Account.vue";
import {ref} from "vue";

const courseName = ref('');
</script>

<template>
  <div class="main">
    <section class="left">
      <Logo />
      <MyCourses />
    </section>
    <section class="center">
      <div class="new-course">
        <BaseInput
            v-model="courseName"
            placeholder="Course topic..." />
        <div class="create-course-link">
          <router-link
              :to="{ path: '/create_course',
              query: {
                courseName: courseName
              } }"
          > >> Create a new course</router-link>
        </div>
      </div>
      <ContinueStudying />
    </section>
    <section class="right">
      <Account />
    </section>
  </div>
</template>

<style scoped>
a {
  font-size: 1rem;
  color: #42A5F5;
  text-decoration: none;
}
a:hover {
  text-decoration: underline;
  cursor: pointer;
}

.main {
  display: flex;
}

.left {
  width: 30vw
}

.center {
  display: flex;
  flex-direction: column;
  width: 40vw;
  align-items: center;
}

.new-course {
  display: flex;
  flex-direction: column;
  padding-top: 5vh;
}

.create-course-link {
  text-align: right;
  margin-top: 0.5vh;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 30vw;
}
</style>
