<script setup>
defineProps({
  title: {
    type: String,
    required: true
  }
})
</script>

<template>
  <button>
    {{ title }}
  </button>
</template>

<style scoped>
button {
  background-color: #48CFAD;
  border-style: none;
  border-radius: 20px;
  padding: 10px 30px;
  font-family: Montserrat, Inter, sans-serif;
  color: #F5F7FA;
  font-size: 14px;
  transition: 500ms;
  cursor: pointer;
}
button:hover {
  background-color: #BBDEFB;
  color: #0D47A1;
}
</style>