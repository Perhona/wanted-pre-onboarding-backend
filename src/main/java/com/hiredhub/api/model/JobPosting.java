package com.hiredhub.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String position;
    private String country;
    private String region;
    private Integer reward;
    @Column(name = "tech_stack")
    private String techStack;
    @Column(name = "job_description")
    private String jobDescription;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public JobPosting() {
    }

    public JobPosting(Long id, String position, String country, String region, Integer reward, String techStack, String jobDescription, Company company) {
        this.id = id;
        this.position = position;
        this.country = country;
        this.region = region;
        this.reward = reward;
        this.techStack = techStack;
        this.jobDescription = jobDescription;
        this.company = company;
        this.createdAt = LocalDateTime.now();
    }
}
