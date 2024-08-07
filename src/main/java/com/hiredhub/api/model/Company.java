package com.hiredhub.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Company() {
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public void addJobPosting(JobPosting jobPosting) {
    }

    public List<JobPosting> getJobPostings() {
        return null;
    }
}
