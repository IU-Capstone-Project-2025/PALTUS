<script setup>
const props = defineProps({
  error_message: {
    type: String,
    required: true,
  }
})
</script>

<template>
  <p>{{ props.error_message }}</p>
</template>

<style scoped>
p {
  font-size: 0.9rem;
  color: #c10404;
  margin: 1vw;
}
</style>