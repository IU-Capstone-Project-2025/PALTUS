<script setup>
import BaseHeader from "@/components/shared/BaseHeader.vue";
import ButtonRed from "@/components/shared/ButtonRed.vue";

defineProps({
  course: {
    type: Object,
    required: true
  },
  chosenContent: {
    type: Number,
    required: true
  }
})

defineEmits(['removeCourse'])
</script>

<template>
  <section class="main-content">
    <div class="lesson-content" v-if="!chosenContent">
      <BaseHeader :text="course.course_name" class="uppercase" />
      <div class="description">
        <h1 class="field-name">
          Description:
        </h1>
        <p class="field-info">{{ course.description }}</p>
      </div>
      <div class="books">
        <div class="field-name">
          Useful books for the course:
        </div>
        <ul>
          <li v-for="book in course.books" class="field-info">
            <p> 📚 {{ book }}</p>
          </li>
        </ul>
      </div>
    </div>
    <div class="navigation" v-if="!chosenContent">
      <ButtonRed title="REMOVE A COURSE" @click="$emit('removeCourse')" />
    </div>
  </section>
</template>

<style scoped>
.main-content {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  padding: 2vh 20vw 2vh 8vw;
}

.uppercase {
  text-transform: uppercase;
  font-size: 2.25rem;
  margin-bottom: 7vh;
}

.field-name {
  font-size: 1.75rem;
  font-weight: bold;
  color: #0D47A1;
  margin-bottom: 1vh;
}

.field-info {
  font-size: 1.25rem;
  color: #0D47A1;
  margin-bottom: 1vh;
}

.description {
  margin-bottom: 4vh;
}

.navigation {
  margin-top: 3vh;
}

ul {
  list-style-type: none;
  padding-left: 1vw;
}
</style>