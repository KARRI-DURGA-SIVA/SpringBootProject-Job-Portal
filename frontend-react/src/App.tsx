import { useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { BriefcaseBusiness, Building2, CalendarCheck, FileText, Search, ShieldCheck, Users } from "lucide-react";
import { jobApi, type Job } from "./api/client";
import { Button } from "./components/ui/button";
import { Input } from "./components/ui/input";

const sampleJobs: Job[] = [
  {
    id: 1,
    title: "Senior Java Developer",
    description: "Build secure Spring Boot APIs for a high-scale hiring platform.",
    salary: "18-28 LPA",
    location: "Hyderabad",
    experience: "5+ years",
    postedDate: new Date().toISOString(),
    status: "OPEN",
    company: { id: 1, companyName: "TalentCloud", location: "Hyderabad" }
  },
  {
    id: 2,
    title: "Frontend Engineer",
    description: "Own React, TypeScript, and accessible job seeker workflows.",
    salary: "12-20 LPA",
    location: "Bengaluru",
    experience: "3+ years",
    postedDate: new Date().toISOString(),
    status: "OPEN",
    company: { id: 2, companyName: "HireStack", location: "Bengaluru" }
  },
  {
    id: 3,
    title: "Recruiter Operations Lead",
    description: "Manage candidate pipelines, interviews, and recruiter analytics.",
    salary: "10-16 LPA",
    location: "Remote",
    experience: "4+ years",
    postedDate: new Date().toISOString(),
    status: "OPEN",
    company: { id: 3, companyName: "PeopleGrid", location: "Remote" }
  }
];

const stats = [
  { label: "Open jobs", value: "1,248", icon: BriefcaseBusiness },
  { label: "Companies", value: "326", icon: Building2 },
  { label: "Applications", value: "8,940", icon: FileText },
  { label: "Interviews", value: "412", icon: CalendarCheck }
];

function App() {
  const [query, setQuery] = useState("Java Developer");
  const [activeRole, setActiveRole] = useState("Job Seeker");

  const jobsQuery = useQuery({
    queryKey: ["jobs", query],
    queryFn: () => jobApi.list(query),
    retry: false
  });

  const jobs = useMemo(() => {
    const source = jobsQuery.data?.length ? jobsQuery.data : sampleJobs;
    const text = query.trim().toLowerCase();
    if (!text || jobsQuery.data?.length) return source;
    return source.filter((job) =>
      [job.title, job.description, job.location, job.company.companyName].some((value) =>
        value?.toLowerCase().includes(text)
      )
    );
  }, [jobsQuery.data, query]);

  return (
    <main className="min-h-screen bg-background">
      <header className="border-b border-border bg-white">
        <div className="mx-auto flex max-w-7xl flex-wrap items-center justify-between gap-4 px-4 py-4 sm:px-6 lg:px-8">
          <div className="flex items-center gap-3">
            <div className="flex h-10 w-10 items-center justify-center rounded-md bg-primary text-white">
              <BriefcaseBusiness size={22} />
            </div>
            <div>
              <h1 className="text-xl font-bold">Job Portal</h1>
              <p className="text-sm text-slate-500">Recruit, apply, shortlist, and track hiring</p>
            </div>
          </div>
          <nav className="flex flex-wrap gap-2">
            {["Job Seeker", "Recruiter", "Admin"].map((role) => (
              <Button
                key={role}
                variant={activeRole === role ? "primary" : "secondary"}
                onClick={() => setActiveRole(role)}
              >
                {role}
              </Button>
            ))}
          </nav>
        </div>
      </header>

      <section className="mx-auto grid max-w-7xl gap-6 px-4 py-8 sm:px-6 lg:grid-cols-[1.5fr_1fr] lg:px-8">
        <div className="rounded-lg border border-border bg-white p-5 shadow-soft">
          <div className="flex flex-col gap-4 lg:flex-row">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-2.5 text-slate-400" size={20} />
              <Input
                className="pl-10"
                value={query}
                onChange={(event) => setQuery(event.target.value)}
                placeholder="Search jobs, skills, companies, or locations"
              />
            </div>
            <Button>Search Jobs</Button>
          </div>
          {jobsQuery.isError && (
            <p className="mt-3 text-sm text-slate-500">
              Showing demo data until the Spring Boot API is running on port 8080.
            </p>
          )}
        </div>

        <div className="grid grid-cols-2 gap-3">
          {stats.map((stat) => (
            <div key={stat.label} className="rounded-lg border border-border bg-white p-4">
              <stat.icon className="mb-3 text-primary" size={22} />
              <p className="text-2xl font-bold">{stat.value}</p>
              <p className="text-sm text-slate-500">{stat.label}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="mx-auto grid max-w-7xl gap-6 px-4 pb-10 sm:px-6 lg:grid-cols-[1.35fr_0.65fr] lg:px-8">
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <h2 className="text-lg font-bold">Recommended Jobs</h2>
            <span className="text-sm text-slate-500">{jobs.length} results</span>
          </div>
          {jobs.map((job) => (
            <article key={job.id} className="rounded-lg border border-border bg-white p-5 shadow-sm">
              <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
                <div>
                  <h3 className="text-lg font-semibold">{job.title}</h3>
                  <p className="mt-1 text-sm font-medium text-slate-600">{job.company.companyName}</p>
                  <p className="mt-3 max-w-2xl text-sm leading-6 text-slate-600">{job.description}</p>
                </div>
                <span className="w-fit rounded-full bg-blue-50 px-3 py-1 text-xs font-semibold text-blue-700">
                  {job.status}
                </span>
              </div>
              <div className="mt-4 flex flex-wrap gap-2 text-sm text-slate-600">
                <span className="rounded-md bg-slate-100 px-3 py-1">{job.location}</span>
                <span className="rounded-md bg-slate-100 px-3 py-1">{job.experience}</span>
                <span className="rounded-md bg-slate-100 px-3 py-1">{job.salary}</span>
              </div>
              <div className="mt-5 flex flex-wrap gap-2">
                <Button>Apply</Button>
                <Button variant="secondary">Save</Button>
                <Button variant="ghost">View details</Button>
              </div>
            </article>
          ))}
        </div>

        <aside className="space-y-4">
          <RolePanel role={activeRole} />
          <div className="rounded-lg border border-border bg-white p-5">
            <h2 className="flex items-center gap-2 text-lg font-bold">
              <ShieldCheck className="text-primary" size={20} />
              Auth & Access
            </h2>
            <p className="mt-3 text-sm leading-6 text-slate-600">
              JWT login, role-based APIs, recruiter approvals, and protected application tracking are wired in the backend.
            </p>
          </div>
        </aside>
      </section>
    </main>
  );
}

function RolePanel({ role }: { role: string }) {
  const items =
    role === "Recruiter"
      ? ["Create company", "Post jobs", "View applicants", "Shortlist candidates", "Schedule interviews"]
      : role === "Admin"
        ? ["Manage users", "Approve recruiters", "Moderate jobs", "Analytics dashboard", "Reports"]
        : ["Create profile", "Upload resume", "Apply to jobs", "Track applications", "Save jobs"];

  return (
    <div className="rounded-lg border border-border bg-white p-5">
      <h2 className="flex items-center gap-2 text-lg font-bold">
        <Users className="text-primary" size={20} />
        {role} Flow
      </h2>
      <ul className="mt-4 space-y-3">
        {items.map((item) => (
          <li key={item} className="flex items-center gap-3 text-sm text-slate-700">
            <span className="h-2 w-2 rounded-full bg-primary" />
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
