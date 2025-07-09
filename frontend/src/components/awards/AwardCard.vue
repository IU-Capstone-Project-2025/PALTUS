<script setup>
const props = defineProps({
  finished: {
    type: Boolean,
    required: true
  }
})
</script>

<template>
  <div class="award-card" v-if="props.finished">
    <div class="award-badge" style="background-color: #42A5F5">
      ✓
    </div>
    <div class="award-info">
      <h3>Hot Streak</h3>
      <p>Log in 3 days in a row</p>
      <div class="award-completed">
        <span>+30 XP</span>
        <span>Completed</span>
      </div>
    </div>
  </div>

  <div class="award-card" v-else>
    <div class="award-badge" style="background-color: #e0e0e0">
    </div>
    <div class="award-info">
      <h3>Learner</h3>
      <p>Complete 5 lessons</p>
      <div class="award-progress">
        <div class="progress-bar" style="width: 60%"></div>
        <span>60% complete</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.award-card {
  display: flex;
  width: 100%;
  max-width: 400px;
  padding: 15px;
  border-radius: 8px;
  background: #F5F7FA;
  border: 1px solid #EEEEEE;
  transition: all 0.2s ease;
}

.award-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.award-badge {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: white;
  margin-right: 20px;
  flex-shrink: 0;
}

.award-info h3 {
  color: var(--text-primary, #000000);
  font-size: 1.1rem;
  font-weight: 500;
  margin-bottom: 5px;
}

.award-info p {
  color: var(--text-secondary, #000000);
  font-size: 0.9rem;
  opacity: 0.8;
  margin-bottom: 10px;
}

.award-progress {
  margin-top: 10px;
}

.award-progress .progress-bar {
  background: var(--progress-background, #e0e0e0);
  height: 6px;
  border-radius: 3px;
  margin-bottom: 5px;
}

.award-progress .progress-bar > div {
  background: #42A5F5;
  height: 100%;
  border-radius: 3px;
}

.award-progress span {
  font-size: 0.8rem;
  color: var(--text-secondary, #000000);
  opacity: 0.6;
}

.award-completed {
  display: flex;
  justify-content: space-between;
  margin-top: 8px;
  font-size: 0.85rem;
}

.award-info {
  flex: 1;
}
</style>