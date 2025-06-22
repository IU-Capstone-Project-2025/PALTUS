<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  lessons_passed: {
    type: Number,
    required: true
  }
})
</script>

<template>
    <div class="course">
      <router-link to="/course/1">
        <h3>
          {{ title }}
        </h3>
        <p>Lesson {{ lessons_passed + 1}}</p>
      </router-link>
    </div>
</template>

<style scoped>
.course {
  box-sizing: border-box;
  width: 18vw;
  height: 10vh;
  align-content: center;
  background-color: #F5F7FA;
  padding-left: 30px;
  border-style: solid;
  border-width: 0 1px 1px 1px;
  border-color: #BBDEFB;
}

h3 {
  font-size: 1.125rem;
  padding-right: 10px;
  color: #1B5E20;
  margin-bottom: 0.5vh;
}

p {
  font-size: 0.75rem;
  color: #48CFAD;
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