<script setup>
/**
 * ChatModal.vue - component for modal page of a chat with LLM inside the lesson,
 * used in Course page from the ContentLesson.vue component
 * Shows Chat Modal and closes it
 */
import BaseHeader from "@/components/shared/BaseHeader.vue";
import Chat from "@/components/course/Chat.vue";
import BaseButton from "@/components/shared/BaseButton.vue";

const props = defineProps({
  topic: {
    type: String,
    required: true,
  },
  id: {
    type: Number,
    required: true,
  },
  lesson: {
    type: Number,
    required: true,
  }
})

defineEmits(['closeModal'])
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-head">
        <BaseHeader :text="props.topic" style="margin-bottom: 2vh"/>
      </div>
      <div class="modal-body">
        <Chat
            :id="props.id"
            :lesson="props.lesson"
        />
      </div>
      <div class="modal-footer">
        <BaseButton
            class="finish-btn"
            title="Finish session"
            @click="$emit('closeModal')"
        />
        <p class="warning">Chat will be deleted after you leave</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 5;
}

.modal-content {
  background: #F5F7FA;
  box-sizing: border-box;
  padding: 3vh 2vw;
  border-radius: 10px;
  width: 60vw;
  height: 90vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  overflow-x: hidden;
}

.modal-body {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 80%;
  align-items: center;
  border-radius: 16px;
}

.modal-head {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-bottom: solid 1px #BBDEFB;
}

.modal-footer {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 1vh;
}

.finish-btn {
  width: 15vw;
}

.warning {
  font-size: 0.8rem;
  margin-top: 0.5vh;
  color: #0D47A1;
}
</style>