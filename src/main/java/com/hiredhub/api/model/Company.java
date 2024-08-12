package com.hiredhub.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Getter
    @OneToMany(mappedBy = "company")
    private final List<JobPosting> jobPostings = new ArrayList<>();

    public Company() {
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public List<Long> getOtherJobPostingIds(Long jobPostingId) {
        return jobPostings.stream().map(JobPosting::getId).filter(id -> !id.equals(jobPostingId)).toList();
    }
}
