<div align="center">
  <h1> 🧠📅 PALTUS - Personalized Adaptive Learning & Time Utilization System</h1>
  <p><strong>AI-Powered Self-Learning Planner</strong></p>
</div>
<p align="center">
  <a href="http://paltus-edu.ru" target="_blank">
    <img src="https://img.shields.io/badge/Demo-Visit-blue?style=for-the-badge&logo=vercel" />
  </a>
</p>

---
An AI-powered self-learning planner that helps users create personalized study plans for any topic, using AI-model to generate lessons, mantain notes to them, and track progress with gamification to maintain engagement. User can add a course using AI-model interaction: user writes that he wants to learn some discipline, adds amount of lessons and available time, then AI-model generates a full course depending on user’s preferences and requested topic. The main goal - courses are built to fit user’s comfort and free time. Lessons include an option to edit the course model or lesson topics, add notes on each lesson, ask AI about the topic, and check the knowledge with quiz generated.

> 🧩 Everything is fully customizable: lesson titles, durations, and more. For each subtopic, users can interact with the AI for feedback, clarification, or course updates.

---


## ✨ Features

### 🔐 User Authentication
- Email verification system  
- JWT-based authorization  
- Password encryption

### 📚 Course Management
- AI-powered course generation via **GigaChat API**  
- Course editing, saving, and deletion  
- Hierarchical structure of the course: Lessons → Subtopics  

### 🧠 Learning System
- Subtopic completion tracking  
- In-lesson note-taking  
- Quiz generation per topic  
- Session-based chat with AI  

### 📈 Progress Tracking
- Last activity tracking  
- Achievement system  
- XP and level-up mechanics  

---
 

## 🚀 Getting Started

### 📋 Prerequisites
- **Node.js** `v18+`
- **Java JDK** `17`
- **PostgreSQL** `14+`

### 🛠️ Setup

1. Get a [GigaChat API key](https://developers.sber.ru/portal/gigachat-and-api)
2. You will need a password for the email to register.
3. Copy and configure the environment file:  
   ```bash
   cp .env.example .env
   ```
4. Run the application:
   ```bash
   docker compose --profile prod up --build -d
   ```

---

## 🧪 Tech Stack

### Frontend
- **Vue 3** + Composition API  
- **Pinia** (state management)  
- **Axios** (HTTP requests)  
- **CSS** (styling)

### Backend
- **Java 17**, **Spring Boot 3**
- **Spring Security**, **JWT**
- **PostgreSQL**
- **GigaChat API** (AI integration)
- **Lombok**, **MapStruct**, **Jsonrepair**
- **Springdoc-openapi** (API docs)
- **Maven** (build)

### DevOps & Infrastructure
- **Docker**
- **Docker profiles** (to mantain dev and prod version of frontend)
- **GitHub Actions** (CI/CD: build, test, deploy)

### Testing
- **JUnit 5**
- **Spring Boot Test**
- **Integration Tests**

---

## 📸 UI / Demo / Screenshots

- 🎨 [Figma Design](https://www.figma.com/design/rvNoC6oOC2Xe5y7yWIhLuN/Demo-visuals?node-id=0-1&p=f&t=3HySqTnuZp6DQNiC-0)  
- 🖥️ [Demo](#)  
- 🖼️ **Screenshots**:
  - Login page
     ![Login page](frontend/docs/images/login.png)
  - Home page
    ![Home page](frontend/docs/images/home.png)
  - Course page
    ![Course page](frontend/docs/images/course.png)
  - Quiz page
    ![Quiz page](frontend/docs/images/quiz.png)
  - Ask AI modal
    ![Ask AI modal](frontend/docs/images/askAI.png)
  - Course creation page
    ![Course creation page](frontend/docs/images/courseCreation.png)
  - Achievements page
    ![Achievements page](frontend/docs/images/achievements.png)

---

## 📄 License

This project is licensed under the **MIT License**.  
See [LICENSE](./LICENSE) for details.

---

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vuejs/vuejs-original.svg" height="40" alt="Vue" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="Java" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="Spring" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="PostgreSQL" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" height="40" alt="Docker" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" height="40" alt="GitHub" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="40" alt="JavaScript" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original.svg" height="40" alt="Maven" />
</div>
