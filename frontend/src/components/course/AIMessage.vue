<script setup>
defineProps({
  message: {
    type: String,
    required: true,
  }
})
</script>

<template>
  <div class="msg-container">
    <div class="msg">
      {{ message }}
    </div>
  </div>
</template>

<style scoped>
.msg-container {
  width: 100%;
  display: flex;
  justify-content: flex-start;
}

.msg {
  padding: 1rem;
  max-width: 10vw;
  background-color: #0D47A1;
  color: #F5F7FA;
  border-radius: 16px;
  margin-bottom: 1vh;
  border: solid 1px #42A5F5;
}
</style>