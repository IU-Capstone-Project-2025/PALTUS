<script setup>
/**
 * ProgressBar.vue - component for the custom animated progress bar,
 */
import {onMounted, ref, watch} from "vue";

const props = defineProps({
  fraction_finished: {
    type: Number,
    required: true,
    // validator: (value) => value >= 0 && value <= 100,
  }
})

const currentProgress = ref(0)

onMounted(() => {
  setTimeout(() => {
    currentProgress.value = props.fraction_finished
  }, 100)
})

watch(
    () => props.fraction_finished,
    (newVal) => {
      currentProgress.value = newVal;
    }
);
</script>

<template>
  <div class="progress-bar">
    <div
        :style="{ width: currentProgress + '%' }"
        class="progress"
    ></div>
  </div>
</template>

<style scoped>
.progress-bar {
  display: flex;
  height: 3vh;
  background-color: #F5F7FA;
  overflow: hidden;
}

.progress {
  background-color: #48CFAD;
  height: 100%;
  transition: width 1s;
}
</style>