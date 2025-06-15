<script setup>

import {useAuthStore} from "@/stores/auth.js";
</script>

<template>
  <div class="profile">
    {{ useAuthStore().user.email }}
  </div>
  <div class="options">
    <ul>
      <li>
        <div class="setting">
          My account
        </div>
      </li>
      <li>
        <div class="setting">
          Settings
        </div>
      </li>
      <li>
        <div class="setting">
          Current level
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

.profile {
  box-sizing: border-box;
  width: 23vw;
  height: 10vh;
  font-size: 18px;
  padding-left: 5vw;
  background-color: #0D47A1;
  align-content: center;
  color: #F5F7FA;
  font-weight: bold;
  border-bottom-left-radius: 16px;
}

.setting {
  box-sizing: border-box;
  width: 14vw;
  height: 10vh;
  align-content: center;
  background-color: #F5F7FA;
  text-align: center;
  border-style: solid;
  border-width: 0 1px 1px 1px;
  border-color: #BBDEFB;
  color: #0D47A1;
}
</style>