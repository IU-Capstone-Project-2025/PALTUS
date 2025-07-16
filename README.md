# PALTUS - AI-Powered Self-Learning Planner 

An AI-powered self-learning planner that helps users create personalized study plans for any topic, using AI-model to generate lessons, structure schedules, and track progress with gamification to maintain engagement. User can add a course using AI-model interaction: user writes that he wants to learn some discipline, adds amount of lessons and available time, then AI-model generates a full course depending on user’s preferences and requested topic. The main goal - courses are built to fit user’s comfort and free time. Lessons include an option to edit the course model or lesson topics, add notes on each lesson and see the description of the generated course. Everything is customisable individually, starting from lesson amount and lesson duration, ending with calendar dates and time. There will be an option to give feedback to in a chat with AI-model after lesson or a course.

## Implemented Features
- **User Authentication**:
  - Email verification system
  - JWT-based authorization
  - Password encryption
- **Course Management**:
  - AI-generated courses via GigaChat API
  - Course editing/saving/deletion
  - Lesson/subtopic hierarchy
- **Learning System**:
  - Subtopic completion tracking
  - Notes management
  - Quiz generation
  - Session based chat for asking question about lesson
- **Progress Tracking**:
  - Last activity monitoring
  - Achievement system
  - XP and level progression
##  Getting Started

### Prerequisites
- Node.js v18+
- Java JDK 17
- PostgreSQL 14+

### Running

Run project via `docker compose --profile prod up --build -d`. You should get [GigaChat API key](https://developers.sber.ru/portal/gigachat-and-api) to run the application. See [.env.example](https://github.com/IU-Capstone-Project-2025/PALTUS/tree/main/.env.example) for configuration.

### Technology Stack
# Frontend
 - Framework: Vue 3 + Composition API
 - State Management: Pinia
 - Styling: CSS
 - HTTP Requests: Axios
# Backend
 - Language: Java 17
 - Framework: Spring Boot 3
 - Database: PostgreSQL
 - AI Integration: GigaChat API
 - Security: Spring Security + JWT
 - Build: Maven
 - Utilities: Lombok, MapStruct
 - API docs: Springdoc-openapi
 - Json fix util: Jsonrepair
# DevOps & Infrastructure
 - Containerization: Docker
 - CI/CD: GitHub Actions (build, test, deploy)
# Testing:
 - JUnit 5
 - Spring Boot Test
 - Integration tests

## Team Members 

| Team Member             | Telegram Alias   | Email Address                     | Track                       |
|-------------------------|------------------|-----------------------------------|-----------------------------|
| Sergey Knyazkin (Lead)  | @poeticlama      | s.knyazkin@innopolis.university   | Frontend/Design/DevOps      |
| Aidar Sarvartdinov      | @aidar_sar       | a.sarvardinov@innopolis.university| Backend                     |
| Amir Fayzullin          | @HoriFa7z        | a.fayzullin@innopolis.university  | Fullstack                   |
| Ramazan Gizamov         | @ramzeuus        | r.gizamov@innopolis.university    | Frontend/Tech communication |
| Igor Dubrovsky          | @chomosuce       | i.dubrovsky@innopolis.university  | Backend/DevOps              |

##  License

This project is licensed under the **MIT License**
