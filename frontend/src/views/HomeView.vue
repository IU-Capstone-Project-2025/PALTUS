<script setup>
import {useAuthStore} from "../stores/auth.js";
import Logo from "@/components/Logo.vue";

const courses = [
  {
    name: 'Introduction to ML',
    lessons_passed: 2
  },
  {
    name: 'Python Programming',
    lessons_passed: 0,
  },
  {
    name: 'English Advanced',
    lessons_passed: 7,
  }
]
const last_course = {
  name: 'Introduction to ML',
  lessons_passed: 2,
  last_lesson: 'Regression and model quality assessment',
  subtopics: [
    'Linear and polynomial regression',
    'Measurement accuracy models: MSE, R²',
    'Cross-validation and regularization'
  ]
}
</script>

<template>
  <div class="main">
    <section class="left">
      <Logo />
      <div class="my-courses">
        MY COURSES:
      </div>
      <ul class="courses">
        <li v-for="course in courses" class="course">
          <h3>
            {{ course.name }}
          </h3>
          <p>Lesson {{ course.lessons_passed + 1}}</p>
        </li>
      </ul>
    </section>
    <section class="center">
      <div class="new-course">
        <input placeholder="Course topic..." />
        <div class="create-course-link">
          <a> >> Create a new course</a>
        </div>
      </div>
      <div class="continue-course">
        <h3>Continue studying:</h3>
        <div class="course-info">
          <div class="course">
            <h3>
              {{ last_course.last_lesson }}
            </h3>
            <p>
              Lecture: {{ last_course.lessons_passed + 1 }}
            </p>
          </div>
          <ul class="subtopics">
            <li v-for="subtopic in last_course.subtopics">
              {{ subtopic }}
            </li>
          </ul>
        </div>
        <div class="go-to-course">
          <button>
            Go to course
          </button>
        </div>
      </div>
    </section>
    <section class="right">
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
    </section>
  </div>
</template>

<style scoped>
ul{
  list-style-type: none;
  padding: 0;
}
input {
  font-family: Montserrat, Inter, sans-serif;
  color: #0D47A1;
  border-style: none;
  border-radius: 20px;
  height: 8vh;
  width: 18vw;
  padding-left: 30px;
  padding-right: 10px;
  font-size: 18px;
}
textarea:focus, input:focus {
  outline: none;
}
::placeholder {
  color: #abd4f6;
}
a {
  font-size: 16px;
  color: #42A5F5;
}
a:hover {
  text-decoration: underline;
  cursor: pointer;
}
button {
  background-color: #48CFAD;
  border-style: none;
  border-radius: 20px;
  padding: 10px 30px;
  font-family: Montserrat, Inter, sans-serif;
  color: #F5F7FA;
  font-size: 14px;
  transition: 500ms;
  cursor: pointer;
}
button:hover {
  background-color: #BBDEFB;
  color: #0D47A1;
}

.main {
  display: flex;
}

.left {
  width: 30vw
}

.my-courses {
  box-sizing: border-box;
  width: 23vw;
  height: 10vh;
  font-size: 20px;
  padding-left: 30px;
  background-color: #42A5F5;
  align-content: center;
  color: #F5F7FA;
  font-weight: bold;
  border-bottom-right-radius: 16px;
}

.course {
  box-sizing: border-box;
  width: 18vw;
  height: 10vh;
  align-content: center;
  background-color: #F5F7FA;
  padding-left: 30px;
  border-style: solid;
  border-width: 0 1px 1px 1px;
  border-color: #BBDEFB;
}

.course h3 {
  font-size: 18px;
  padding-right: 10px;
  color: #1B5E20;
  margin-bottom: 0.5vh;
}

.course p {
  font-size: 12px;
  color: #48CFAD;
}

.center {
  display: flex;
  flex-direction: column;
  width: 40vw;
  align-items: center;
}

.new-course {
  display: flex;
  flex-direction: column;
  padding-top: 5vh;
}

.create-course-link {
  text-align: right;
  margin-top: 0.5vh;
}

.continue-course {
  box-sizing: border-box;
  background-color: #42A5F5;
  margin-top: 15vh;
  padding: 20px 20px;
  border-radius: 16px;
}

.continue-course > h3 {
  font-size: 24px;
  font-weight: bold;
  color: #F5F7FA;
  margin-bottom: 5vh
}

.course-info {
  display: flex;
  align-items: center;
  margin-bottom: 3vh;
}

.course-info .course {
  border-radius: 20px;
  width: 18vw;
  height: 13vh;
  margin-right: 3vw;
}

.course-info ul {
  list-style-type: disc;
  width: 12vw;
  font-size: 12px;
  color: #F5F7FA;
}

.course-info ul li {
  margin-bottom: 0.5vh;
}

.go-to-course {
  padding-left: 20vw;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 30vw;
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
