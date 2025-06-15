<script setup>
defineProps({
  placeholder: {
    type: String,
    required: true,
  }
})
</script>

<template>
  <input :placeholder="placeholder" />
</template>

<style scoped>
input {
  font-family: Montserrat, Inter, sans-serif;
  color: #0D47A1;
  border-style: none;
  border-radius: 20px;
  height: 8vh;
  width: 18vw;
  padding-left: 30px;
  padding-right: 10px;
  font-size: 18px;
}

textarea:focus, input:focus {
  outline: none;
}
::placeholder {
  color: #abd4f6;
}
</style>