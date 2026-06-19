CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(40) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE companies (
  id BIGSERIAL PRIMARY KEY,
  company_name VARCHAR(255) NOT NULL,
  website VARCHAR(255),
  location VARCHAR(255),
  description TEXT,
  logo_url VARCHAR(1000)
);

CREATE TABLE jobs (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  salary VARCHAR(120),
  location VARCHAR(255),
  experience VARCHAR(120),
  company_id BIGINT NOT NULL REFERENCES companies(id),
  posted_date TIMESTAMPTZ NOT NULL DEFAULT now(),
  status VARCHAR(40) NOT NULL DEFAULT 'OPEN'
);

CREATE TABLE resumes (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id),
  file_url VARCHAR(1000) NOT NULL,
  uploaded_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE applications (
  id BIGSERIAL PRIMARY KEY,
  job_id BIGINT NOT NULL REFERENCES jobs(id),
  user_id BIGINT NOT NULL REFERENCES users(id),
  resume_url VARCHAR(1000) NOT NULL,
  status VARCHAR(60) NOT NULL DEFAULT 'APPLIED',
  applied_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_jobs_search ON jobs USING gin (to_tsvector('english', coalesce(title, '') || ' ' || coalesce(description, '') || ' ' || coalesce(location, '')));
CREATE INDEX idx_applications_user ON applications(user_id);
CREATE INDEX idx_applications_job ON applications(job_id);
