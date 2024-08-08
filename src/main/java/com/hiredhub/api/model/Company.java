package com.hiredhub.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Getter
    @OneToMany(mappedBy = "company")
    private final List<JobPosting> jobPostings = new ArrayList<>();
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
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
        jobPostings.add(jobPosting);
    }
}
