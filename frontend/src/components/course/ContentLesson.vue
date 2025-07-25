<script setup>
/**
 * ContentLesson.vue - component for lesson information,
 * used in Course page from Content page component
 */
import BaseHeader from "@/components/shared/BaseHeader.vue";
import Notes from "@/components/course/Notes.vue";
import NotesEdition from "@/components/course/NotesEdition.vue";
import BaseCheckbox from "@/components/course/BaseCheckbox.vue";
import ErrorNotification from "@/components/shared/ErrorNotification.vue";
import BaseButton from "@/components/shared/BaseButton.vue";

defineProps({
  course: {
    type: Object,
    required: true,
  },
  chosenContent: {
    type: Number,
    required: true,
  },
  waiting: {
    type: Boolean,
    required: true,
  },
  editMode: {
    type: Object,
    required: true,
  },
  error: {
    type: Boolean,
    required: true,
  }
})

defineEmits(['editNotes', 'submitNotes', 'openChat', 'generateQuiz', 'checkSubtopic'])
</script>

<template>
  <div v-if="chosenContent" class="lesson-content">
    <BaseHeader :text="course.lessons[chosenContent - 1].title + ':'" class="uppercase"/>
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
                @update:modelValue="$emit('checkSubtopic', subtopic.id, subtopic.finished)"
            />
            <label :for="subtopic.topic" class="field-info" style="font-weight: 600">{{ subtopic.topic }}: </label>
          </div>
          <Notes
              v-if="editMode.id !== subtopic.id"
              :id="subtopic.id"
              :notes="subtopic.notes"
              @editNotes="$emit('editNotes', subtopic.id)"
          />
          <NotesEdition
              v-else
              v-model:notes="subtopic.notes"
              @submitNotes="$emit('submitNotes', subtopic.notes)"
          />
          <BaseButton
              v-if="editMode.id !== subtopic.id"
              class="ai-btn"
              title="Ask PALTUS"
              @click="$emit('openChat', subtopic.topic, subtopic.id)"
          />
        </li>
      </ul>
      <div v-if="course.lessons[chosenContent - 1].finished">
        <div
            v-if="!course.lessons[chosenContent - 1].quiz"
            class="quiz-btn"
        >
          <div class="quiz-interaction">
            <BaseButton
                v-if="!waiting"
                color="green"
                title="Start a quiz"
                @click="$emit('generateQuiz')"
            />
            <BaseButton
                v-else
                color="inactive"
                title="Start a quiz"
            />
            <ErrorNotification v-if="error" error_message="Server error, try again"/>
          </div>
        </div>
        <div v-else class="passed">
          ✓ Quiz is passed
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
ul {
  list-style-type: none;
}

.lesson-content {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  padding: 2vh 20vw 2vh 8vw;
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

.uppercase {
  text-transform: uppercase;
  font-size: 2.25rem;
  margin-bottom: 7vh;
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

.ai-btn {
  margin-left: 2vw;
  margin-bottom: 3vh;
}

.quiz-btn {
  width: 100%;
  display: flex;
  justify-content: flex-end;
}

.quiz-interaction {
  display: flex;
  flex-direction: column;
}

.passed {
  text-align: end;
  margin-top: 3vh;
  color: #48CFAD;
  font-weight: 700;
  font-size: 1.3rem;
}
</style>