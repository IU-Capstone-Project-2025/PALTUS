<script setup>
import BaseHeader from "@/components/shared/BaseHeader.vue";
import {ref} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const course = ref(route.state?.course || null);
</script>

<template>
  <div class="container">
    <BaseHeader :text="course.name" class="course-name" />
  </div>
</template>

<style scoped>

</style>