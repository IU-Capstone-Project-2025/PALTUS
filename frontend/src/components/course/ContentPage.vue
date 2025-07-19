<script setup>
/**
 * ContentPage.vue - component for course content,
 * used in Course page
 * Sends requests to mark subtopics, edit notes, generate quizzes
 */
import axios from "@/plugins/axios.js";
import router from "@/router/index.js";
import {nextTick, reactive, ref} from "vue";
import ChatModal from "@/components/course/ChatModal.vue";
import {useCourseStore} from "@/stores/course.js";
import {useQuizStore} from "@/stores/quiz.js";
import {useAuthStore} from "@/stores/auth.js";
import ContentLesson from "@/components/course/ContentLesson.vue";
import ContentCourse from "@/components/course/ContentCourse.vue";

const editMode = reactive({
  id: null,
  edit: false
});

const modal = ref(false);
const modalTopic = ref('');
const modalId = ref(null);
const quiz = useQuizStore();
const waiting = ref(false);
const error = ref(false);

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
    props.subtopicsChanged.push({id, finished});
  }
  if (props.course.lessons[props.chosenContent - 1].subtopics.every(subtopic => subtopic.finished &&
      props.subtopicsChanged.every(subtopic => subtopic.finished))) {
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
          headers: {"Content-Type": "text/plain"}
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

const generateQuiz = async () => {
  error.value = false;
  const lessonId = props.course.lessons[props.chosenContent - 1].id;
  try {
    waiting.value = true;
    await quiz.loadQuiz(lessonId);
  } catch (err) {
    if (err.statusCode === 406) {
      error.value = true;
    } else if (err.statusCode === 401) {
      useAuthStore().logout();
    }
    console.error(error);
  }
}
</script>

<template>
  <ChatModal
      v-if="modal"
      :id="modalId"
      :lesson="props.course.lessons[props.chosenContent - 1].id"
      :topic="modalTopic"
      @close-modal="finishChat"
  />
  <ContentLesson
      v-if="chosenContent"
      :chosenContent="chosenContent"
      :course="course"
      :editMode="editMode"
      :error="error"
      :waiting="waiting"
      @checkSubtopic="checkSubtopic"
      @editNotes="editNotes"
      @generateQuiz="generateQuiz"
      @openChat="openChat"
      @submitNotes="submitNotes"
  />

  <ContentCourse
      v-else
      :chosenContent="chosenContent"
      :course="course"
      @removeCourse="removeCourse"
  />
</template>

<style scoped>
</style>