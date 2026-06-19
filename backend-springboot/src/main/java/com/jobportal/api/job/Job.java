package com.jobportal.api.job;

import com.jobportal.api.company.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(length = 5000)
    private String description;
    private String salary;
    private String location;
    private String experience;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    @NotNull
    private Company company;

    @Column(name = "posted_date", nullable = false)
    private Instant postedDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.OPEN;

    protected Job() {
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSalary() { return salary; }
    public String getLocation() { return location; }
    public String getExperience() { return experience; }
    public Company getCompany() { return company; }
    public Instant getPostedDate() { return postedDate; }
    public JobStatus getStatus() { return status; }

    public void updateFrom(Job other) {
        this.title = other.title;
        this.description = other.description;
        this.salary = other.salary;
        this.location = other.location;
        this.experience = other.experience;
        this.company = other.company;
        this.status = other.status;
    }
}
