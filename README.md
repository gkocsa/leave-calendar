# Team Leave Calendar

A simple full-stack web application for managing team leave requests and viewing a weekly on-call rotation.

## Features

- View team members
- Create leave requests
- List leave requests
- Approve or reject leave requests
- Delete leave requests
- Prevent overlapping leave requests for the same team member
- View weekly on-call schedule
- Highlight conflicts when the on-call person has approved leave

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- React + Vite
- Docker Compose

## Running the Application

You can run the application using docker. 

Make sure the following ports are free: 8080, 5173, and 5434

From the project root:

```bash
docker compose up --build
```

Frontend: http://localhost:5173
Backend:  http://localhost:8080

Example backend endpoint:
http://localhost:8080/api/team-members

## Assumptions
- No authentication is required.
- Anyone using the app can approve or reject leave requests.
- Leave dates are inclusive (One day leaves are possible).
- On-call weeks run Monday to Sunday.
- Only approved leave creates on-call conflicts.

## Features not completed

## Optional improvements
- endpoints for deleting, updating leave requests
- Dockerization
