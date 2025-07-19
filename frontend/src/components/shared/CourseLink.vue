<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  next_lesson: {
    type: Number,
    required: false
  },
  id: {
    type: Number,
    required: true
  }
})

const link = `course/${props.id}`;
</script>

<template>
  <div class="course">
    <router-link :to="link">
      <h3>
        {{ props.title }}
      </h3>
      <p v-if="next_lesson">Lesson {{ props.next_lesson }}</p>
      <p v-else>Next Lesson</p>
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