<script setup>
/**
 * Timer.vue - component for quiz timer,
 * used in Quiz page but may be reused for other pages in the future
 */
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