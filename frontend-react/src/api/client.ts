export type Role = "ROLE_ADMIN" | "ROLE_RECRUITER" | "ROLE_JOB_SEEKER";

export type Company = {
  id: number;
  companyName: string;
  website?: string;
  location?: string;
  description?: string;
  logoUrl?: string;
};

export type Job = {
  id: number;
  title: string;
  description?: string;
  salary?: string;
  location?: string;
  experience?: string;
  postedDate: string;
  status: "DRAFT" | "OPEN" | "CLOSED";
  company: Company;
};

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? "";

export async function api<T>(path: string, init?: RequestInit): Promise<T> {
  const token = localStorage.getItem("job_portal_token");
  const response = await fetch(`${API_BASE}${path}`, {
    ...init,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...init?.headers
    }
  });
  if (!response.ok) {
    throw new Error(await response.text());
  }
  if (response.status === 204) {
    return undefined as T;
  }
  return response.json() as Promise<T>;
}

export const jobApi = {
  list: (query?: string) => api<Job[]>(`/api/jobs${query ? `?query=${encodeURIComponent(query)}` : ""}`),
  get: (id: number) => api<Job>(`/api/jobs/${id}`)
};

export const companyApi = {
  list: () => api<Company[]>("/api/companies")
};
