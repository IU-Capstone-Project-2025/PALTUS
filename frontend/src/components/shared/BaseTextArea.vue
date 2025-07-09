<script setup>
import { onMounted, ref, watch } from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    required: true,
  },
  placeholder: {
    type: String,
    required: true,
  }
});

const emit = defineEmits(['update:modelValue']);
const textareaRef = ref(null);

const autoResize = () => {
  const el = textareaRef.value;
  if (el) {
    el.style.height = 'auto';
    el.style.height = el.scrollHeight + 'px';
  }
};

watch(() => props.modelValue, autoResize);
onMounted(autoResize);
</script>

<template>
  <textarea
      ref="textareaRef"
      :placeholder="placeholder"
      :value="modelValue"
      @input="event => emit('update:modelValue', event.target.value)"
      class="input-textarea"
      rows="1"
  />
</template>

<style scoped>
.input-textarea {
  box-sizing: border-box;
  font-family: Montserrat, Inter, sans-serif;
  color: #0D47A1;
  border-style: none;
  border-radius: 20px;
  min-height: 4rem;
  width: 100%;
  padding: 1.25rem;
  font-size: 1.125rem;
  line-height: 1.5rem;
  resize: none;
  overflow: hidden;
  background-color: white;
}

.input-textarea:focus {
  outline: none;
}

::placeholder {
  color: #abd4f6;
}
</style>
