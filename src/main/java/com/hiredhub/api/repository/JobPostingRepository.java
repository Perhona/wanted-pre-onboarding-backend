package com.hiredhub.api.repository;

import com.hiredhub.api.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findJobPostingsByCompanyNameContainingIgnoreCase(String companyName);
    List<JobPosting> findJobPostingsByPositionContainingIgnoreCase(String position);
    List<JobPosting> findJobPostingsByCompanyNameContainingIgnoreCaseAndPositionContainingIgnoreCase(String companyName, String position);
}
