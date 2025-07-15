<script setup>
import BaseTextArea from "@/components/shared/BaseTextArea.vue";
import {ref} from "vue";
import UserMessage from "@/components/course/UserMessage.vue";
import AIMessage from "@/components/course/AIMessage.vue";

const query = ref('');

const userMessages = [
    'Hello brother',
    'I dont understand this topic at all??',
    'dyou know what i mean this is so fucking hard maaaaan',
    'goddaaaaaaaamn'
]

const aiMessages = [
  'hello maaaaaaan',
  'whats your problem piece of shit',
  'fuck you brotha) ima gigachat man',
]
</script>

<template>
<div class="chat">
  <div class="messages-container">
    <ul class="messages" v-if="userMessages.length > 0">
      <li class="message" v-for="(msg, index) in userMessages" :key="index">
        <UserMessage :message="msg" />
        <AIMessage v-if="aiMessages.length > index" :message="aiMessages[index]" />
      </li>
    </ul>
  </div>
  <BaseTextArea
      placeholder="Ask your question"
      :model-value="query"
      class="query-input"
  />
</div>
</template>

<style scoped>
ul {
  list-style-type: none;
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
  width: 70%;
  max-height: 30%;
  border: solid 1px #BBDEFB;
  margin-bottom: 1vh;
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
}

.messages {
  width: 90%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>