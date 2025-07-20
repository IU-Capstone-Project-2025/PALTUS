<script setup>
/**
 * LevelBadge.vue - component for user title,
 * used in Awards page
 */
const props = defineProps({
  title: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <div class="badge">
    {{ props.title }}
  </div>
</template>

<style scoped>
.badge {
  width: 13vw;
  text-align: center;
  background: #42A5F5;
  color: #F5F7FA;
  padding: 1.5vh 3vw;
  border-radius: 24px;
  font-size: 1.1rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  box-shadow: 0 4px 12px rgba(66, 165, 245, 0.3);
  margin-bottom: 1.5vh;
}
</style>