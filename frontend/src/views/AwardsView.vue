<script setup>
import {ref} from "vue";
import Logo from "@/components/shared/Logo.vue";
import Account from "@/components/shared/Account.vue";
import BaseHeader from "@/components/shared/BaseHeader.vue";
import LevelBadge from "@/components/awards/LevelBadge.vue";
import ProgressBar from "@/components/shared/ProgressBar.vue";
import AwardCard from "@/components/awards/AwardCard.vue";

const status = ref('BEGINNER PALTUS');
const level = ref(1);
const xp = ref(150);
const next_level_xp = ref(500);
const awards = ref([1, 2, 3])
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
    </section>
    <section class="right">
      <Account />
    </section>
  </div>

  <section class="awards-container">
  <div class="awards-section">
    <h2 class="section-title">Available Awards</h2>
    <ul class="awards-grid">
      <li v-for="award in awards">
        <AwardCard :finished="false" />
      </li>
    </ul>
  </div>

  <div class="streak-container-large">
    <div class="streak-content">
      <span class="streak-icon-large">🔥</span>
      <div class="streak-text-large">
        <div class="streak-title">Current Streak</div>
        <div class="streak-days">3 days</div>
      </div>
    </div>
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
  width: 100%;
  max-width: 1000px;
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
  margin-bottom: 8px;
  font-size: 0.9rem;
  color: var(--text-primary, #000000);
}

.progress-bar {
  background: #F0F0F0;
  height: 1vh;
  border-radius: 4px;
  width: 100%;
}

.progress-fill {
  border: none;
}

.awards-section {
  background: var(--background-element, #ffffff);
  border-radius: 12px;
  padding: 25px;
  border: 1px solid var(--border-color, #eeeeee);
}

.section-title {
  color: #42A5F5;
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 25px;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 1px solid var(--border-color, #f0f0f0);
}

.awards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 15px;
  justify-items: center;
}

.award-completed span:first-child {
  color: #42A5F5;
  font-weight: 500;
}

.award-completed span:last-child {
  color: var(--text-secondary, #000000);
  opacity: 0.7;
}

.streak-container-large {
  margin-top: 40px;
  display: flex;
  justify-content: center;
}

.streak-content {
  display: flex;
  align-items: center;
  gap: 20px;
  background: rgba(66, 165, 245, 0.1);
  padding: 25px 50px;
  border-radius: 50px;
  border: 2px solid rgba(66, 165, 245, 0.3);
}

.streak-icon-large {
  font-size: 3rem;
}

.streak-text-large {
  display: flex;
  flex-direction: column;
  color: #42A5F5;
  font-family: 'Montserrat', sans-serif;
}

.streak-title {
  font-size: 1.2rem;
  font-weight: 500;
  opacity: 0.9;
}

.streak-days {
  font-size: 2rem;
  font-weight: 600;
  line-height: 1.2;
}

ul {
  list-style-type: none;
}

@media (max-width: 768px) {
  .awards-view {
    padding: 15px;
  }

  .awards-container {
    padding: 20px;
  }

  .awards-grid {
    grid-template-columns: 1fr;
  }

  .award-card {
    max-width: 100%;
  }

  .streak-content {
    padding: 20px 30px;
  }

  .streak-icon-large {
    font-size: 2.5rem;
  }

  .streak-days {
    font-size: 1.8rem;
  }
}
</style>