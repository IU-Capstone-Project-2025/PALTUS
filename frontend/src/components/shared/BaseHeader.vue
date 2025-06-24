<script setup>
defineProps({
  text: {
    type: String,
    required: true,
  }
})
</script>

<template>
  <h1>
    {{ text }}
  </h1>
</template>

<style scoped>
h1 {
  color: #42A5F5;
  font-size: 1.35rem;
  padding: 0.5vh 0;
  font-weight: bold;
}
</style>