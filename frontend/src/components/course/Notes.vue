<script setup>
/**
 * Notes.vue - component for notes displaying,
 * used in Course page from Content Course component
 */
defineProps({
  notes: {
    type: String,
    required: true,
  },
  id: {
    type: Number,
    required: true,
  }
})

defineEmits(['editNotes'])
</script>

<template>
  <div class="notes">
    <p class="instruction">Click inside to add notes</p>
    <div class="notes-container" @click="$emit('editNotes', id)">
      <a v-if="notes" class="edit-notes">{{ notes }}</a>
      <a v-else class="add-notes">Add your notes here</a>
    </div>
  </div>
</template>

<style scoped>
.notes {
  font-size: 1.25rem;
  color: #0D47A1;
  margin-bottom: 1vh;
  margin-left: 2vw;
}

.instruction {
  margin-left: 1vw;
  font-size: 0.95rem;
  color: #42A5F5;
}

.notes-container {
  border: solid 3px #b2d9fa;
  border-radius: 20px;
  margin-bottom: 2vh;
  padding: 1vh 2vw;
  background-color: #F5F7FA;
  cursor: pointer;
  overflow-wrap: break-word;
}

.edit-notes {
  text-decoration: none;
  color: #0D47A1;
  white-space: pre-line;
}

.add-notes {
  text-decoration: none;
  color: #BBDEFB;
  white-space: pre-line;
}
</style>