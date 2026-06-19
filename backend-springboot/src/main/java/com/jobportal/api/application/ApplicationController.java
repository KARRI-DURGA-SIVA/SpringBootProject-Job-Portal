package com.jobportal.api.application;

import com.jobportal.api.job.JobRepository;
import com.jobportal.api.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationRepository applications;
    private final JobRepository jobs;
    private final UserRepository users;

    public ApplicationController(ApplicationRepository applications, JobRepository jobs, UserRepository users) {
        this.applications = applications;
        this.jobs = jobs;
        this.users = users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    JobApplication apply(@Valid @RequestBody ApplicationRequest request, Authentication authentication) {
        var job = jobs.findById(request.jobId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        var user = users.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return applications.save(new JobApplication(job, user, request.resumeUrl()));
    }

    @GetMapping("/my")
    List<JobApplication> my(Authentication authentication) {
        return applications.findByUserEmail(authentication.getName());
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER','ROLE_ADMIN')")
    List<JobApplication> byJob(@PathVariable Long jobId) {
        return applications.findByJobId(jobId);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER','ROLE_ADMIN')")
    JobApplication updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
        var application = applications.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        application.setStatus(status);
        return applications.save(application);
    }
}
