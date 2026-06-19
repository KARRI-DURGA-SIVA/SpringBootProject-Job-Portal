# Architecture

```text
React + TypeScript frontend
        |
        v
Spring Boot REST APIs
        |
        +--> Redis cache
        |
        v
PostgreSQL database
        |
        +--> Email service
        +--> Cloudflare R2 / S3
        +--> Search engine
```

## Roles

- `ROLE_ADMIN`
- `ROLE_RECRUITER`
- `ROLE_JOB_SEEKER`

## Main flows

Recruiter: login, create company, post jobs, review applicants, shortlist, schedule interviews.

Job seeker: register, create profile, upload resume URL, search jobs, apply, track applications, save jobs.

Admin: manage users, manage companies, manage jobs, approve recruiters, view analytics and reports.

## Next production integrations

- Refresh tokens and token rotation.
- OAuth2 Google login.
- Cloudflare R2 presigned upload URLs.
- Email provider for recruiter notifications and interview scheduling.
- OpenSearch/Elasticsearch for richer job search.
- Recruiter approval workflow and audit logs.
