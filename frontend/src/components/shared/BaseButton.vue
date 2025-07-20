<script setup>
/**
 * BaseButton.vue - component for custom button component,
 * Has only 3 main colors
 */
defineProps({
  title: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'button',
  },
  color: {
    type: String,
    default: 'default'
  }
})
</script>

<template>
  <button
      v-if="color === 'green'"
      :type="type"
      class="green"
  >
    {{ title }}
  </button>
  <button
      v-else-if="color === 'red'"
      :type="type"
      class="red"
  >
    {{ title }}
  </button>
  <button
      v-else-if="color === 'inactive'"
      :type="type"
      class="inactive"
  >
    {{ title }}
  </button>
  <button
      v-else-if="color === 'default'"
      :type="type"
      class="default"
  >
    {{ title }}
  </button>


</template>

<style scoped>
button {
  border-style: none;
  border-radius: 20px;
  padding: 1.5vh 2vw;
  font-family: Montserrat, Inter, sans-serif;
  color: #F5F7FA;
  font-size: 0.875rem;
  transition: 500ms;
  cursor: pointer;
}

.default {
  background-color: #42A5F5;
}

.default:hover {
  background-color: #BBDEFB;
  color: #0D47A1;
}

.green {
  background-color: #48CFAD;
}

.green:hover {
  background-color: #BBDEFB;
  color: #0D47A1;
}

.red {
  background-color: #CF4848;
}

.red:hover {
  background-color: #ff0000;
}

.inactive {
  background-color: #BBDEFB;
  color: #0D47A1;
  cursor: not-allowed;
}
</style>