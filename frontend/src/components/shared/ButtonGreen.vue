<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'button',
  }
})
</script>

<template>
  <button :type="type">
    {{ title }}
  </button>
</template>

<style scoped>
button {
  background-color: #48CFAD;
  border-style: none;
  border-radius: 20px;
  padding: 1.5vh 2vw;
  font-family: Montserrat, Inter, sans-serif;
  color: #F5F7FA;
  font-size: 0.875rem;
  transition: 500ms;
  cursor: pointer;
}
button:hover {
  background-color: #BBDEFB;
  color: #0D47A1;
}
</style>