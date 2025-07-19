<script setup>
/**
 * AwardsView.vue - achievements page,
 * available only with authorization, shows user achievements, level, and title
 */
import {onBeforeMount, reactive, ref} from "vue";
import Logo from "@/components/shared/Logo.vue";
import Account from "@/components/shared/Account.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import LevelBadge from "@/components/awards/LevelBadge.vue";
import ProgressBar from "@/components/shared/ProgressBar.vue";
import Streak from "@/components/awards/Streak.vue";
import Achievements from "@/components/awards/Achievements.vue";
import {useAuthStore} from "@/stores/auth.js";
import axios from "@/plugins/axios.js";

onBeforeMount(async () => {
  try {
    const response = await axios.get('/achievements');
    level.value = response.level;
    currentExp.value = response.currentExp;
    requiredExp.value = response.requiredExp;
    title.value = response.title;
    streak.value = response.streak;
    achievements = response.achievements;
  } catch (e) {
    if (e.response.status === 401) {
      useAuthStore().logout();
    }
    console.error(e);
  }
})

const streak = ref(null);
const title = ref('');
const level = ref(null);
const currentExp = ref(null);
const requiredExp = ref(null);
let achievements = reactive([]);
</script>

<template>
  <div class="main">
    <section class="left">
      <Logo/>
    </section>

    <section class="center">
      <div class="user-level">
        <LevelBadge :title="title"/>
        <BaseHeader class="achievements-header" text="Your achievements"/>
      </div>

      <div class="level-progress">
        <div class="progress-info">
          <span>Level {{ level }}</span>
          <span>{{ currentExp }}/{{ requiredExp }} XP</span>
        </div>
        <ProgressBar :fraction_finished="currentExp / requiredExp * 100" class="progress-fill"/>
      </div>
      <Streak v-if="streak" :streak="streak"/>
    </section>
    <section class="right">
      <Account/>
    </section>
  </div>

  <Achievements :achievements="achievements" class="awards-container"/>
</template>


<style scoped>
.main {
  display: flex;
}

.left {
  width: 30vw
}

.center {
  display: flex;
  flex-direction: column;
  width: 40vw;
  align-items: center;
  padding: 5vh 0;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 30vw;
}

.awards-container {
  width: 80vw;
  margin: 0 auto 5vh;
  border-radius: 16px;
  padding: 2rem 1rem 3rem;
}

.user-level {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.achievements-header {
  font-size: 2rem;
}

.level-progress {
  width: 30vw;
  background: #F5F7FA;
  border-radius: 10px;
  padding: 1vw;
  border: 1px solid #EEEEEE;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1vh;
  font-size: 0.9rem;
  color: #0D47A1;
}

.progress-fill {
  background: #F0F0F0;
  height: 1vh;
  border-radius: 4px;
  width: 100%;
}
</style>