<template>
  <div class="awards-view">
    <div class="awards-container">
      <div class="awards-header">
        <div class="user-level">
          <div class="level-badge">BEGINNER PALTUS</div>
          <h1>Your Achievements</h1>
        </div>
      </div>

      <div class="level-progress">
        <div class="progress-info">
          <span>Level 1</span>
          <span>150/500 XP</span>
        </div>
        <div class="progress-bar">
          <div class="progress-fill" style="width: 30%"></div>
        </div>
      </div>

      <div class="awards-section">
        <h2 class="section-title">Available Awards</h2>
        <div class="awards-grid">
          <div class="award-card">
            <div class="award-badge" style="background-color: #42A5F5">
              ✓
            </div>
            <div class="award-info">
              <h3>First Steps</h3>
              <p>Complete your first lesson</p>
              <div class="award-completed">
                <span>+50 XP</span>
                <span>Completed</span>
              </div>
            </div>
          </div>

          <div class="award-card">
            <div class="award-badge" style="background-color: #e0e0e0">
            </div>
            <div class="award-info">
              <h3>Learner</h3>
              <p>Complete 5 lessons</p>
              <div class="award-progress">
                <div class="progress-bar" style="width: 60%"></div>
                <span>60% complete</span>
              </div>
            </div>
          </div>

          <div class="award-card">
            <div class="award-badge" style="background-color: #e0e0e0">
            </div>
            <div class="award-info">
              <h3>Deep Dive</h3>
              <p>Rewind a lesson 3 times</p>
              <div class="award-progress">
                <div class="progress-bar" style="width: 30%"></div>
                <span>30% complete</span>
              </div>
            </div>
          </div>

          <div class="award-card">
            <div class="award-badge" style="background-color: #42A5F5">
              ✓
            </div>
            <div class="award-info">
              <h3>Hot Streak</h3>
              <p>Log in 3 days in a row</p>
              <div class="award-completed">
                <span>+30 XP</span>
                <span>Completed</span>
              </div>
            </div>
          </div>

          <div class="award-card">
            <div class="award-badge" style="background-color: #e0e0e0">
            </div>
            <div class="award-info">
              <h3>Quiz Expert</h3>
              <p>Complete 10 quizzes</p>
              <div class="award-progress">
                <div class="progress-bar" style="width: 10%"></div>
                <span>10% complete</span>
              </div>
            </div>
          </div>

          <div class="award-card">
            <div class="award-badge" style="background-color: #e0e0e0">
            </div>
            <div class="award-info">
              <h3>Perfect</h3>
              <p>Get a 100% in 10 tests</p>
              <div class="award-progress">
                <div class="progress-bar" style="width: 5%"></div>
                <span>5% complete</span>
              </div>
            </div>
          </div>
        </div>
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
    </div>
  </div>
</template>


<style scoped>
.awards-view {
  width: 100%;
  min-height: 100vh;
  padding: 30px 20px;
  font-family: 'Montserrat', sans-serif;
  color: #000;
}

.awards-container {
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
  background: var(--background-content, #ffffff);
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.awards-header {
  text-align: center;
  margin-bottom: 30px;
}

.user-level {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.level-badge {
  background: #42A5F5;
  color: white;
  padding: 12px 30px;
  border-radius: 24px;
  font-size: 1.2rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  box-shadow: 0 4px 12px rgba(66, 165, 245, 0.3);
  margin-bottom: 15px;
}

.awards-header h1 {
  color: #42A5F5;
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 5px;
}

.level-progress {
  background: var(--background-element, #ffffff);
  border-radius: 10px;
  padding: 20px;
  margin: 0 auto 30px;
  max-width: 600px;
  border: 1px solid var(--border-color, #eeeeee);
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  font-size: 0.9rem;
  color: var(--text-primary, #000000);
}

.progress-bar {
  background: var(--progress-background, #f0f0f0);
  height: 8px;
  border-radius: 4px;
  width: 100%;
}

.progress-fill {
  background: #42A5F5;
  height: 100%;
  border-radius: 4px;
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

.award-card {
  display: flex;
  width: 100%;
  max-width: 400px;
  padding: 15px;
  border-radius: 8px;
  background: var(--background-element, #ffffff);
  border: 1px solid var(--border-color, #eeeeee);
  transition: all 0.2s ease;
}

.award-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.award-badge {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: white;
  margin-right: 20px;
  flex-shrink: 0;
}

.award-info {
  flex: 1;
}

.award-info h3 {
  color: var(--text-primary, #000000);
  font-size: 1.1rem;
  font-weight: 500;
  margin-bottom: 5px;
}

.award-info p {
  color: var(--text-secondary, #000000);
  font-size: 0.9rem;
  opacity: 0.8;
  margin-bottom: 10px;
}

.award-progress {
  margin-top: 10px;
}

.award-progress .progress-bar {
  background: var(--progress-background, #e0e0e0);
  height: 6px;
  border-radius: 3px;
  margin-bottom: 5px;
}

.award-progress .progress-bar > div {
  background: #42A5F5;
  height: 100%;
  border-radius: 3px;
}

.award-progress span {
  font-size: 0.8rem;
  color: var(--text-secondary, #000000);
  opacity: 0.6;
}

.award-completed {
  display: flex;
  justify-content: space-between;
  margin-top: 8px;
  font-size: 0.85rem;
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