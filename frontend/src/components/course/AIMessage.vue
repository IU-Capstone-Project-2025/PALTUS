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
    <a class="add-to-notes">
      Add to notes
    </a>
  </div>
</template>

<style scoped>
.msg-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.msg {
  padding: 1rem;
  max-width: 17vw;
  background-color: #0D47A1;
  color: #F5F7FA;
  border-radius: 16px;
  border: solid 1px #42A5F5;
  overflow: hidden;
  word-break: break-word;
}

.add-to-notes {
  font-size: 0.8rem;
  margin-bottom: 1vh;
  color: #0D47A1;
  margin-left: 1vw;
  cursor: pointer;
}
.add-to-notes:hover {
  color: #42A5F5;
  text-decoration: underline;
}
</style>