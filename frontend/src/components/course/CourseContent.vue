<script setup>
import BaseCheckbox from "@/components/shared/BaseCheckbox.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import ButtonRed from "@/components/shared/ButtonRed.vue";
import axios from "@/plugins/axios.js";
import router from "@/router/index.js";
import {nextTick, reactive, ref, watch} from "vue";
import Notes from "@/components/course/Notes.vue";
import NotesEdition from "@/components/course/NotesEdition.vue";
import ButtonDefault from "@/components/shared/ButtonDefault.vue";
import ChatModal from "@/components/course/ChatModal.vue";
import {useCourseStore} from "@/stores/course.js";

const editMode = reactive({
  id: null,
  edit: false
});

const modal = ref(false);
const modalTopic = ref('');
const modalId = ref(null);

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
  if (props.course.lessons[props.chosenContent - 1].subtopics.every(subtopic => subtopic.finished)) {
    saveSubtopics();
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

const openChat = (topic, id) => {
  modalTopic.value = topic;
  modalId.value = id;
  modal.value = true;
}

const finishChat = () => {
  useCourseStore().loadCourse(props.course.courseId);
  nextTick(() => {
    modalTopic.value = '';
    modalId.value = null;
    modal.value = false;
  })
}

const saveSubtopics = async () => {
  for (const subtopicChanged of props.subtopicsChanged) {
    await useCourseStore().updateSubtopic(subtopicChanged, props.course.lessons[props.chosenContent - 1].id);
    await useCourseStore().loadCourse(props.course.courseId);
  }
}
</script>

<template>
  <ChatModal
      v-if="modal"
      :topic="modalTopic"
      :id="modalId"
      :lesson="props.course.lessons[props.chosenContent - 1].id"
      @close-modal="finishChat"
  />
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
          <NotesEdition
              v-model:notes="subtopic.notes"
              @submitNotes="submitNotes"
              v-else
          />
          <ButtonDefault
              v-if="editMode.id !== subtopic.id"
              title="Ask PALTUS"
              class="ai-btn"
              @click="openChat(subtopic.topic, subtopic.id)"
          />
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

.ai-btn {
  margin-left: 2vw;
  margin-bottom: 3vh;
}
</style>