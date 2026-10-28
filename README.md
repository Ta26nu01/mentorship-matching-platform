# Mentorship Matching Platform

A Spring Boot REST API that connects **mentors** and **mentees**. People register with their skills, interests and experience, and the system ranks the most suitable mentors for each mentee and lets the mentee send a request.

> Data is kept in memory (Java lists) while the app runs. It is lost when the app stops. A database can be added later.

## Features

- Register mentors and mentees
- Automatic matching with a score for every mentor
- Mentors have a limit on how many mentees they can take
- One mentee can have only one mentor
- Clear error responses (400, 404, 409) for wrong input, missing ids, duplicate emails and full mentors

## Tech stack

- Java 17+
- Spring Boot (Spring Web)
- Maven
- Tested with Postman

## How matching works

For each mentor with a free slot:

1. Find the topics the mentee **wants to learn** that the mentor **can teach**. If there are none, the mentor is skipped.
2. Score = **5 points** per topic the mentor can teach + **2 points** per shared interest.
3. **+5 points** if the mentor has more years of experience than the mentee.

Mentors are returned from the highest score to the lowest.

## Run the project

1. Clone the repository
2. Open it in an IDE (IntelliJ, Eclipse, VS Code) or use the terminal:
   ```
   mvn spring-boot:run
   ```
3. The API starts at `http://localhost:8080`

## API endpoints

| Method | URL | Purpose |
|---|---|---|
| POST | `/api/mentors` | Register a mentor |
| GET | `/api/mentors` | List all mentors |
| POST | `/api/mentees` | Register a mentee |
| GET | `/api/mentees` | List all mentees |
| GET | `/api/mentees/{menteeId}/matches` | Ranked mentors for a mentee |
| POST | `/api/mentees/{menteeId}/request/{mentorId}` | Mentee picks a mentor |

### Example: register a mentor

```json
{
  "name": "Asha",
  "email": "asha@x.com",
  "skills": "java, ml",
  "interests": "backend, ai",
  "experience": 6,
  "maxMentees": 2
}
```

### Example: register a mentee

For a mentee, `interests` means the topics they want to learn.

```json
{
  "name": "Riya",
  "email": "riya@x.com",
  "skills": "dsa",
  "interests": "java, ai",
  "experience": 1,
  "goal": "Become a backend developer"
}
```

Then `GET /api/mentees/1/matches` returns Asha with a score of 12 (one teachable topic = 5, one shared interest = 2, more experience = 5).

## Test with Postman

Import `Mentorship-API.postman_collection.json` (in the `postman` folder). It contains sample mentors, mentees, matching steps and error cases.

## Project structure

```
src/main/java/com/example/mentorship/
├── Mentor.java                 mentor details
├── Mentee.java                 mentee details
├── MatchingService.java        scoring logic
└── MentorshipController.java   API endpoints
```

## Future improvements

- Store data in MySQL using Spring Data JPA
- Login and passwords
- Let mentors accept or reject requests
- Availability and time zone in matching
- A web front end