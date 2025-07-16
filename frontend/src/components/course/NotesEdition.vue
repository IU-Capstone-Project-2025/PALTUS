<script setup>
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import BaseTextArea from "@/components/shared/BaseTextArea.vue";

defineProps({
  notes: {
    type: String,
    required: true,
  },
})
defineEmits(['submitNotes', 'update:notes'])
</script>

<template>
  <div class="editing">
    <BaseTextArea
        :modelValue="notes"
        @update:modelValue="(value) => $emit('update:notes', value)"
        placeholder="Add your notes here"
    />
    <ButtonGreen title="Submit changes" @click="$emit('submitNotes', notes)" class="submit-btn" />
  </div>
</template>

<style scoped>
.editing {
  margin-left: 2vw;
}

.editing textarea {
  font-size: 0.9rem;
}

.submit-btn {
  margin: 1vh 0 3vh;
}
</style>