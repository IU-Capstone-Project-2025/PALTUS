<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  link: {
    type: String,
    required: true
  }
})
</script>

<template>
  <router-link :to="link">
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