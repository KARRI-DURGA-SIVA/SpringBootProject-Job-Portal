package com.jobportal.api.application;

import com.jobportal.api.job.Job;
import com.jobportal.api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    @NotNull
    private Job job;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "resume_url", nullable = false)
    @NotBlank
    private String resumeUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @Column(name = "applied_date", nullable = false)
    private Instant appliedDate = Instant.now();

    protected JobApplication() {
    }

    public JobApplication(Job job, User user, String resumeUrl) {
        this.job = job;
        this.user = user;
        this.resumeUrl = resumeUrl;
    }

    public Long getId() { return id; }
    public Job getJob() { return job; }
    public User getUser() { return user; }
    public String getResumeUrl() { return resumeUrl; }
    public ApplicationStatus getStatus() { return status; }
    public Instant getAppliedDate() { return appliedDate; }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
