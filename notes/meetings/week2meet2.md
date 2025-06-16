## Requests from frontend #
1. POST request for adding a course:
  ```
  curl -X POST http://localhost:8080/createCourse \
  -H "Content-Type: application/json" \
  -d '{
    "courseName": "Java Fundamentals",
    "goal": "Become a confident Java developer",
    "knowledge": "I know basic programming in Python",
    "lessons": 10,
    "time": "45 minutes"
}'
```
2. GET request for dashboard. JSON from backend:
   ```
   {
     my-courses: [
       {
         course_id: 228,
         title: 'Some title',
         lessons_number: 10, (not now, probably useless)
         next_lesson_num: 3 (not now)
       }
     ],
     next_lesson : { (not now)
       course_id: 228,
       title: 'Some title',
       subtopics: [
         'topic1',
         'topic2'
       ]
     }
   }
   ```
3. User entitiy mantains all user's courses
4. Inside a course mantain a time when a course was accessed last time, and next lesson to learn
5. Add passed_lessons/uncompleted_lessons as field for course
