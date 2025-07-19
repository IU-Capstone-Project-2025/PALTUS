<script setup>
import ProgressBar from "@/components/shared/ProgressBar.vue";

const props = defineProps({
  achievement: {
    type: Object,
    required: true
  }
})
</script>

<template>
  <div class="award-card">
    <div v-if="props.achievement.completed" class="award-badge" style="background-color: #48CFAD">
      ✓
    </div>
    <div v-else class="award-badge" style="background-color: #BBDEFB">
      ✗
    </div>
    <div class="award-info">
      <h3>{{ achievement.name }}</h3>
      <p>{{ achievement.description }}</p>
      <div class="award-progress">
        <ProgressBar v-if="achievement.completed" :fraction_finished="achievement.progress" class="award-progress-bar"/>
        <ProgressBar v-else :fraction_finished="achievement.progress" class="award-progress-bar"/>
        <span>Completed {{ achievement.progress }}%</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.award-card {
  display: flex;
  width: 20vw;
  height: 15vh;
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
  color: #0D47A1;
  font-size: 1.1rem;
  font-weight: 500;
  margin-bottom: 5px;
}

.award-info p {
  color: #42A5F5;
  font-size: 0.9rem;
  margin-bottom: 10px;
  opacity: 0.8;
}

.award-progress {
  margin-top: 10px;
}

.award-progress-bar {
  height: 1vh;
  border-radius: 4px;
  background-color: #BBDEFB;
}

.award-progress span {
  font-size: 0.8rem;
  color: #0D47A1;
  opacity: 0.8;
}

.award-info {
  flex: 1;
}
</style>