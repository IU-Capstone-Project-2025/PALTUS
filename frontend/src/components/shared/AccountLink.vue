<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  link: {
    type: String,
    required: false
  },
  isFunc: {
    type: Boolean,
    default: false
  }
})

defineEmits(['logout'])
</script>

<template>
  <div class="setting" v-if="isFunc" @click="$emit('logout')">
    {{ title }}
  </div>
  <router-link :to="link" v-else>
    <div class="setting">
      {{ title }}
    </div>
  </router-link>
</template>

<style scoped>
.setting {
  box-sizing: border-box;
  width: 14vw;
  height: 10vh;
  align-content: center;
  background-color: #F5F7FA;
  text-align: center;
  border-style: solid;
  border-width: 0 1px 1px 1px;
  border-color: #BBDEFB;
  color: #0D47A1;
  cursor: pointer;
  font-size: 1.125rem;
}

a {
  text-decoration: none;
  color: inherit;
  cursor: pointer;
}
a:hover {
  text-decoration: none;
}
</style>