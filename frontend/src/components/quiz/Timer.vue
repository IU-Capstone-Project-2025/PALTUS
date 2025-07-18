<script setup>
defineProps({
  time: Number,
  required: true
})

const formatTime = (seconds) => {
  const mins = Math.floor(seconds / 60);
  const secs = seconds % 60;
  return `${mins}:${secs < 10 ? '0' : ''}${secs}`;
};
</script>

<template>
  <div class="timer">
    {{ formatTime(time) }}
  </div>
</template>

<style scoped>

</style>