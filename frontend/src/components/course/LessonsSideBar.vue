<script setup>
defineProps({
  lessons: {
    type: Array,
    required: true,
  }
})
</script>

<template>
  <ul class="course-list">
    <li class="lesson-item" v-for="lesson in lessons">
      <div
          class="lesson-chosen"
          @click="emit('update:modelValue', lesson.lesson_number)"
          v-if="lesson.lesson_number === modelValue"
      >
        {{ `${lesson.lesson_number}. ${lesson.title}` }}
      </div>
      <div
          class="lesson"
          style="opacity: 0.3"
          @click="emit('update:modelValue', lesson.lesson_number)"
          v-else-if="lesson.finished"
      >
        {{ `${lesson.lesson_number}. ${lesson.title}` }}
      </div>
      <div
          class="lesson"
          @click="emit('update:modelValue', lesson.lesson_number)"
          v-else
      >
        {{ `${lesson.lesson_number}. ${lesson.title}` }}
      </div>
    </li>
  </ul>
</template>

<style scoped>

</style>