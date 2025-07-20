<script setup>
/**
 * Streak.vue - component for user streak (number of days learning in a row),
 * used in Awards page
 */
defineProps({
  streak: {
    type: Number,
    required: true,
  }
})
</script>

<template>
  <div class="streak-container-large">
    <div class="streak-content">
      <span class="streak-icon-large">🔥</span>
      <div class="streak-text-large">
        <div class="streak-title">Current Streak</div>
        <div class="streak-days">{{ streak }} days</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.streak-container-large {
  margin-top: 40px;
  display: flex;
  justify-content: center;
}

.streak-content {
  display: flex;
  align-items: center;
  gap: 20px;
  background: rgba(66, 165, 245, 0.1);
  padding: 25px 50px;
  border-radius: 50px;
  border: 2px solid rgba(66, 165, 245, 0.3);
}

.streak-icon-large {
  font-size: 3rem;
}

.streak-text-large {
  display: flex;
  flex-direction: column;
  color: #42A5F5;
  font-family: 'Montserrat', sans-serif;
}

.streak-title {
  font-size: 1.2rem;
  font-weight: 500;
  opacity: 0.9;
}

.streak-days {
  font-size: 2rem;
  font-weight: 600;
  line-height: 1.2;
}
</style>