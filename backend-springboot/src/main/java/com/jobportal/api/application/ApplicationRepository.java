package com.jobportal.api.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserEmail(String email);
    List<JobApplication> findByJobId(Long jobId);
}
