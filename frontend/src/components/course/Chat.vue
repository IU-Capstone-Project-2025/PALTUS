<script setup>
import BaseTextArea from "@/components/shared/BaseTextArea.vue";
import {nextTick, onMounted, ref} from "vue";
import UserMessage from "@/components/course/UserMessage.vue";
import AIMessage from "@/components/course/AIMessage.vue";
import Typing from "@/components/course/Typing.vue";

onMounted(() => {
  scrollToBottom();
});

const messagesContainer = ref(null);
const query = ref('');

const userMessages = ref([
    'Hello brother',
    'I dont understand this topic at all??',
    'dyou know what i mean this is so fucking hard maaaaan',
])

const aiMessages = ref([
  'hello maaaaaaan',
  'whats your problem piece of shit',
  'fuck you brotha) ima gigachat man',
])

const waiting = ref(false);

const askAI = () => {
  userMessages.value.push(query.value);
  waiting.value = true;
  query.value = '';

  scrollToBottom();
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
  <div class="messages-container" ref="messagesContainer">
    <ul class="messages" v-if="userMessages.length > 0">
      <li class="message" v-for="(msg, index) in userMessages" :key="index">
        <UserMessage :message="msg" />
        <AIMessage v-if="aiMessages.length > index" :message="aiMessages[index]" />
        <Typing v-else />
      </li>
    </ul>
  </div>
  <div class="query-input">
    <BaseTextArea
        placeholder="Ask your question"
        v-model="query"
        class="message-input"
    />
    <div
        class="submit-msg"
        @click="askAI"
        v-if="!waiting && query.length"
    >
      ↑
    </div>
    <div class="submit-inactive" v-else>
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