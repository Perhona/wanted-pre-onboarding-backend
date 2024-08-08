package com.hiredhub.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity(name = "job_posting")
public class JobPosting {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String position;
    @Getter
    private String country;
    @Getter
    private String region;
    @Getter
    private Integer reward;
    @Getter
    @Column(name = "tech_stack")
    private String techStack;
    @Getter
    @Column(name = "job_description")
    private String jobDescription;
    @Getter
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public JobPosting() {
    }

    public JobPosting(String position, String country, String region, Integer reward, String techStack, String jobDescription, Company company) {
        this(null, position, country, region, reward, techStack, jobDescription, company);
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
