package com.hiredhub.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class Application {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    private LocalDateTime createdAt;

    public Application() {
    }

    public Application(User user, JobPosting jobPosting) {
        this(null, user, jobPosting);
    }

    public Application(Long id, User user, JobPosting jobPosting) {
        this.id = id;
        this.user = user;
        this.jobPosting = jobPosting;
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getJobPostingId() {
        return jobPosting.getId();
    }
}
