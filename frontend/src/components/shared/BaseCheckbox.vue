<script setup>
defineProps({
  id: {
    type: String,
    required: true
  },
  modelValue: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:modelValue'])

const toggleCheckbox = (event) => {
  emit('update:modelValue', event.target.checked)
}
</script>

<template>
  <div class="checkbox-container">
    <input
        type="checkbox"
        :id="id"
        class="custom-checkbox"
        :checked="modelValue"
        @change="toggleCheckbox"
    />
    <label :for="id" class="checkbox-label"></label>
  </div>
</template>

<style scoped>
.checkbox-container {
  position: relative;
  display: inline-block;
  margin-right: 12px;
}

.custom-checkbox {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.checkbox-label {
  display: inline-block;
  width: 20px;
  height: 20px;
  background-color: #fff;
  border: 1px solid #0D47A1;
  border-radius: 2px;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
}

.custom-checkbox:checked + .checkbox-label {
  background-color: #0D47A1;
  border-color: #0D47A1;
}

.checkbox-label:after {
  content: "";
  position: absolute;
  display: none;
  left: 6px;
  top: 2px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.custom-checkbox:checked + .checkbox-label:after {
  display: block;
}

.custom-checkbox:focus + .checkbox-label {
  box-shadow: 0 0 0 2px rgba(13, 71, 161, 0.2);
}
</style>