<script setup>
defineProps({
  modelValue: {
    type: String,
    required: true,
  },
  placeholder: {
    type: String,
    required: true,
  }
})
const emit = defineEmits(['update:modelValue'])
</script>

<template>
  <input
      :placeholder="placeholder"
      :value="modelValue"
      @input="event => emit('update:modelValue', event.target.value)"/>
</template>

<style scoped>
input {
  box-sizing: border-box;
  font-family: Montserrat, Inter, sans-serif;
  color: #0D47A1;
  border-style: none;
  border-radius: 20px;
  min-height: 4rem;
  height: 4rem;
  width: 20vw;
  padding-left: 30px;
  padding-right: 10px;
  font-size: 1.125rem;
}

textarea:focus, input:focus {
  outline: none;
}
::placeholder {
  color: #abd4f6;
}
</style>