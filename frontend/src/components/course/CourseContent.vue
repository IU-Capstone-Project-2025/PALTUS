<script setup>
import BaseCheckbox from "@/components/shared/BaseCheckbox.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import ButtonRed from "@/components/shared/ButtonRed.vue";
import axios from "@/plugins/axios.js";
import router from "@/router/index.js";
import {reactive} from "vue";
import BaseTextArea from "@/components/shared/BaseTextArea.vue";
import ButtonGreen from "@/components/shared/ButtonGreen.vue";
import Notes from "@/components/course/Notes.vue";

const editMode = reactive({
  id: null,
  edit: false
});

const props = defineProps({
  course: {
    type: Object,
    required: true,
  },
  chosenContent: {
    type: Number,
    required: true,
  },
  subtopicsChanged: {
    type: Array,
    required: true,
  }
})

const checkSubtopic = (id, finished) => {
  const index = props.subtopicsChanged.findIndex(item => item.id === id);
  if (index > -1) {
    props.subtopicsChanged.splice(index, 1);
  } else {
    props.subtopicsChanged.push({ id, finished });
  }
};

const removeCourse = async () => {
  try {
      axios.delete(`courses/${props.course.courseId}`).then(() => {
      router.push('/');
    });
  } catch (error) {
    console.error(error);
  }
}

const editNotes = (id) => {
  editMode.edit = true;
  editMode.id = id;
}

const submitNotes = (notes) => {
  try {
    axios.put(
        `lessons/${props.course.lessons[props.chosenContent - 1].id}/subtopics/setNotes/${editMode.id}`,
        notes,
        {
          headers:{ "Content-Type": "text/plain"}
        }
    );
    editMode.edit = false;
    editMode.id = null;
  } catch (error) {
    console.error(error);
  }
}
</script>

<template>
  <div class="lesson-content" v-if="chosenContent">
    <BaseHeader :text="course.lessons[chosenContent - 1].title + ':'" class="uppercase" />
    <div class="subtopics">
      <div class="field-name">
        Subtopics:
      </div>
      <ul class="subtopics-list">
        <li v-for="subtopic in course.lessons[chosenContent - 1].subtopics">
          <div class="subtopic">
            <BaseCheckbox
                :id="subtopic.topic"
                v-model="subtopic.finished"
                @update:modelValue="checkSubtopic(subtopic.id, subtopic.finished)"
            />
            <label :for="subtopic.topic" class="field-info" style="font-weight: 600">{{ subtopic.topic }}: </label>
          </div>
          <Notes
              v-if="editMode.id !== subtopic.id"
              :notes="subtopic.notes"
              :id="subtopic.id"
              @editNotes="editNotes"
          />
          <div class="editing" v-else-if="editMode.id === subtopic.id">
            <BaseTextArea
                v-model="subtopic.notes"
                placeholder="Add your notes here"
            />
            <ButtonGreen title="Submit changes" @click="submitNotes(subtopic.notes)" class="submit-btn" />
          </div>
        </li>
      </ul>
    </div>
  </div>
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
            <p> {{ book }}</p>
          </li>
        </ul>
      </div>
    </div>
    <div class="navigation" v-if="!chosenContent">
      <ButtonRed title="REMOVE A COURSE" @click="removeCourse" />
    </div>
  </section>
</template>

<style scoped>
.main-content {
  display: flex;
  flex-direction: column;
  align-content: space-between;
}

.lesson-content {
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

.subtopics {
  margin-bottom: 4vh;
}

.subtopics-list {
  padding: 0;
  list-style: none;
}

.subtopic {
  display: flex;
}

.navigation {
  padding-left: 8vw;
}

ul {
  list-style-type: none;
}

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