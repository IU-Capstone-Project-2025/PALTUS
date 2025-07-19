<script setup>
import BaseButton from "@/components/shared/BaseButton.vue";
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
        placeholder="Add your notes here"
        @update:modelValue="(value) => $emit('update:notes', value)"
    />
    <BaseButton
        class="submit-btn"
        color="green"
        title="Submit changes"
        @click="$emit('submitNotes', notes)"
    />
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