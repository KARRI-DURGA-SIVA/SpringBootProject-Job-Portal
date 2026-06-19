# Job Portal

A full-stack job portal starter built with React, TypeScript, Vite, Tailwind CSS, React Query, Redux Toolkit, Java 21, Spring Boot, Spring Security, Spring Data JPA, Hibernate, JWT, PostgreSQL, and Redis.

## Structure

```text
job-portal
├── frontend-react
├── backend-springboot
├── database
├── docker-compose.yml
└── docs
```

## Run with Docker

```bash
docker compose up --build
```

Frontend: `http://localhost:5173`

Backend: `http://localhost:8080`

## Run locally

Start PostgreSQL and Redis, then:

```bash
cd backend-springboot
mvn spring-boot:run
```

```bash
cd frontend-react
npm install
npm run dev
```

## Implemented

- JWT registration and login.
- Role-based API authorization.
- Companies, jobs, resumes, and applications APIs.
- Job search endpoint with Redis cache annotation.
- React dashboard with job search, role panels, and API fallback data.
- Docker Compose for frontend, backend, PostgreSQL, and Redis.
