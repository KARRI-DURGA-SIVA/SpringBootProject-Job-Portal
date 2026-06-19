# Job Portal API

Base URL: `http://localhost:8080`

## Auth

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/google` currently returns `501` until OAuth client setup is added.

Register body:

```json
{
  "name": "Asha Rao",
  "email": "asha@example.com",
  "password": "password123",
  "role": "ROLE_JOB_SEEKER"
}
```

## Jobs

- `GET /api/jobs`
- `GET /api/jobs?query=java`
- `GET /api/jobs/{id}`
- `POST /api/jobs` recruiter/admin only
- `PUT /api/jobs/{id}` recruiter/admin only
- `DELETE /api/jobs/{id}` recruiter/admin only

## Companies

- `GET /api/companies`
- `POST /api/companies` recruiter/admin only

## Applications

- `POST /api/applications` job seeker only
- `GET /api/applications/my`
- `GET /api/applications/job/{jobId}` recruiter/admin only
- `PUT /api/applications/{id}/status?status=SHORTLISTED` recruiter/admin only

## Resumes

- `POST /api/resumes`
- `GET /api/resumes/my`

The resume endpoint stores a URL. Cloudflare R2/S3 upload signing can be added behind this contract.
