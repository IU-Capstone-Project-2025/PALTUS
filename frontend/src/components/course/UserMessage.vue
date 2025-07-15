<script setup>
defineProps({
  message: {
    type: String,
    required: true
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
  justify-content: flex-end;
}

.msg {
  padding: 1rem;
  max-width: 25vw;
  background-color: #BBDEFB;
  color: #0D47A1;
  border-radius: 16px;
  margin-bottom: 1vh;
  border: solid 1px #98c9f1;
  overflow: hidden;
  word-break: break-word;
  white-space: pre-line;
}
</style>