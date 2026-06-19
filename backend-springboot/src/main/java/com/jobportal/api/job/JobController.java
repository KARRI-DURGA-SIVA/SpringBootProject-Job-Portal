package com.jobportal.api.job;

import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobRepository jobs;

    public JobController(JobRepository jobs) {
        this.jobs = jobs;
    }

    @GetMapping
    @Cacheable(value = "jobs", key = "#query ?: 'all'")
    List<Job> all(@RequestParam(required = false) String query) {
        if (query == null || query.isBlank()) {
            return jobs.findAll();
        }
        return jobs.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(
                query, query, query
        );
    }

    @GetMapping("/{id}")
    Job one(@PathVariable Long id) {
        return jobs.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER','ROLE_ADMIN')")
    Job create(@Valid @RequestBody Job job) {
        return jobs.save(job);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER','ROLE_ADMIN')")
    Job update(@PathVariable Long id, @Valid @RequestBody Job request) {
        Job job = one(id);
        job.updateFrom(request);
        return jobs.save(job);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER','ROLE_ADMIN')")
    void delete(@PathVariable Long id) {
        jobs.deleteById(id);
    }
}
