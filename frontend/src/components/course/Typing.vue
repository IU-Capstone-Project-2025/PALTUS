<script setup>
/**
 * Typing.vue - style component for displaying waiting of LLM answer,
 * used in Chat modal page with Chat component
 */
</script>

<template>
  <div class="typing-indicator">
    <span class="dot dot-1">.</span>
    <span class="dot dot-2">.</span>
    <span class="dot dot-3">.</span>
  </div>
</template>

<style scoped>
.typing-indicator {
  display: inline-flex;
  padding-left: 2vw;
  background-color: #F5F7FA;
  color: #42A5F5;
}

.dot {
  font-size: 3rem;
  opacity: 0;
  animation: dot-animation 1.5s infinite ease-in-out;
}

.dot-1 {
  animation-delay: 0s;
}

.dot-2 {
  animation-delay: 0.5s;
}

.dot-3 {
  animation-delay: 1s;
}

@keyframes dot-animation {
  0% {
    opacity: 0;
    transform: translateY(0);
  }
  50% {
    opacity: 1;
    transform: translateY(-2px);
  }
  100% {
    opacity: 0;
    transform: translateY(0);
  }
}
</style>