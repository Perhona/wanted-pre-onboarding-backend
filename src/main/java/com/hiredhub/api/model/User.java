package com.hiredhub.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Application> applications = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, List<Application> applications) {
        this.id = id;
        this.name = name;
        this.applications = applications;
    }

    public boolean hasApplied(JobPosting jobPosting) {
        return applications.stream().anyMatch(application -> application.getJobPosting().equals(jobPosting));
    }
}
