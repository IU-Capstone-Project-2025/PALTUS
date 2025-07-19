<script setup>
import AwardCard from "@/components/awards/AwardCard.vue";

defineProps({
  achievements: {
    type: Array,
    required: true,
  }
})
</script>

<template>
  <div class="awards-section">
    <h2 class="section-title">Available Awards</h2>
    <ul class="awards-grid">
      <li v-for="achievement in achievements" class="grid-element">
        <AwardCard :achievement="achievement"/>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.awards-section {
  background: white;
  border-radius: 12px;
  padding: 2vw;
}

.section-title {
  color: #42A5F5;
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 25px;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.awards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(20vw, 1fr));
  gap: 1vh;
  justify-items: center;
}

ul {
  list-style-type: none;
}
</style>