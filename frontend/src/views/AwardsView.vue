<script setup>
import {reactive, ref} from "vue";
import Logo from "@/components/shared/Logo.vue";
import Account from "@/components/shared/Account.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import LevelBadge from "@/components/awards/LevelBadge.vue";
import ProgressBar from "@/components/shared/ProgressBar.vue";
import AwardCard from "@/components/awards/AwardCard.vue";
import Streak from "@/components/awards/Streak.vue";

const status = ref('BEGINNER PALTUS');
const level = ref(1);
const xp = ref(150);
const next_level_xp = ref(500);
const awards = reactive([{
  name: "First steps",
  description: "Complete your first lesson",
  finished: true,
  xp: 50,
}, {
  name: "Learner",
  description: "Complete 5 lessons",
  finished: false,
  xp: 100,
}, {
  name: "Hot streak",
  description: "Learn 3 days in a row",
  finished: true,
  xp: 30,
}, {
  name: "Hot streak",
  description: "Learn 3 days in a row",
  finished: true,
  xp: 30,
}]);
const streak = ref(3);
</script>

<template>
  <div class="main">
    <section class="left">
      <Logo />
    </section>

    <section class="center">
      <div class="user-level">
        <LevelBadge :title="status" />
        <BaseHeader text="Your achievements" class="achievements-header" />
      </div>

      <div class="level-progress">
        <div class="progress-info">
          <span>Level {{ level }}</span>
          <span>{{ xp }}/{{ next_level_xp }} XP</span>
        </div>
        <ProgressBar :fraction_finished="xp / next_level_xp * 100" class="progress-fill" />
      </div>
      <Streak :streak="streak" v-if="streak" />
    </section>
    <section class="right">
      <Account />
    </section>
  </div>

  <section class="awards-container">
    <div class="awards-section">
      <h2 class="section-title">Available Awards</h2>
      <ul class="awards-grid">
        <li v-for="award in awards" class="grid-element">
          <AwardCard :award="award" />
        </li>
      </ul>
    </div>
  </section>
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
  margin: 0 auto;
  border-radius: 16px;
  padding: 30px;
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

.awards-section {
  background: white;
  border-radius: 12px;
  padding: 2vw;
}

.section-title {
  color: #42A5F5;
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 25px;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.awards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(20vw, 1fr));
  gap: 1vh;
  justify-items: center;
}

ul {
  list-style-type: none;
}
</style>