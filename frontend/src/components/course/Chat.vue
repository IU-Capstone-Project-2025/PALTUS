<script setup>
/**
 * Chat.vue - component for chat with LLM inside the lesson,
 * used in Chat Modal page
 * Sends messages to LLM, adds info to the notes
 */
import BaseTextArea from "@/components/shared/BaseTextArea.vue";
import {nextTick, onMounted, reactive, ref} from "vue";
import MessageUser from "@/components/course/MessageUser.vue";
import MessageAi from "@/components/course/MessageAI.vue";
import Typing from "@/components/course/Typing.vue";
import axios from "@/plugins/axios.js";
import {useAuthStore} from "@/stores/auth.js";

onMounted(() => {
  scrollToBottom();
  sessionId.value = '';
});

const props = defineProps({
  id: {
    type: Number,
    required: true,
  },
  lesson: {
    type: Number,
    required: true,
  }
})

const messagesContainer = ref(null);
const query = ref('');
const waiting = ref(false);
const sessionId = ref('');
const userMessages = ref([])
const aiMessages = reactive([])


const askAI = async () => {
  userMessages.value.push(query.value);
  waiting.value = true;
  scrollToBottom();
  const requestBody = {
    request: query.value,
    sessionId: sessionId.value,
  }
  query.value = '';
  try {
    const response = await axios.post(`/subtopicAskLLM/${props.id}`, requestBody);
    sessionId.value = response.sessionId;
    waiting.value = false;
    const newMessage = {
      message: response.response,
      added: false,
    }
    aiMessages.push(newMessage);
    scrollToBottom();
  } catch (e) {
    console.error(e)
    if (e.statusCode === 401) {
      useAuthStore().logout();
    }
  }
}

const addToNotes = async (message) => {
  try {
    await axios.put(
        `/lessons/${props.lesson}/subtopics/addNotes/${props.id}`,
        message.message,
        {
          headers: {"Content-Type": "text/plain"}
        }
    );
    message.added = true;
  } catch (error) {
    console.error(error);
  }
}

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTo({
        top: messagesContainer.value.scrollHeight,
        behavior: 'smooth'
      });
    }
  });
};
</script>

<template>
  <div class="chat">
    <div ref="messagesContainer" class="messages-container">
      <ul v-if="userMessages.length > 0" class="messages">
        <li v-for="(msg, index) in userMessages" :key="index" class="message">
          <MessageUser :message="msg"/>
          <MessageAi
              v-if="aiMessages.length > index"
              v-model="aiMessages[index].added"
              :message="aiMessages[index].message"
              @add="addToNotes(aiMessages[index])"
          />
          <Typing v-else/>
        </li>
      </ul>
    </div>
    <div class="query-input">
      <BaseTextArea
          v-model="query"
          class="message-input"
          placeholder="Ask your question"
      />
      <div
          v-if="!waiting && query.length"
          class="submit-msg"
          type="submit"
          @click="askAI"
      >
        ↑
      </div>
      <div v-else class="submit-inactive">
        ↑
      </div>
    </div>
  </div>
</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

.chat {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  box-sizing: border-box;
}

.query-input {
  width: 100%;
  max-height: 30%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.message-input {
  width: 70%;
  border: solid 1px #BBDEFB;
  margin: 1vh 0.5vw 1vh 0;
  max-height: 10vh;
}

.submit-msg {
  height: 3rem;
  width: 3rem;
  font-size: 1.25rem;
  color: #F5F7FA;
  background-color: #0D47A1;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
  transition: 0.3s;
}

.submit-msg:hover {
  height: 3rem;
  width: 3rem;
  font-size: 1.25rem;
  color: #F5F7FA;
  background-color: #42A5F5;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
}

.submit-inactive {
  height: 3rem;
  width: 3rem;
  font-size: 1.25rem;
  color: #F5F7FA;
  background-color: #0D47A1;
  opacity: 30%;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: not-allowed;
}

.messages-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  overflow-y: auto;
  flex-grow: 1;
  scrollbar-width: none;
  padding-top: 2vh;
  align-items: center;
  border-bottom: solid 1px #BBDEFB;
}

.messages {
  width: 80%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>