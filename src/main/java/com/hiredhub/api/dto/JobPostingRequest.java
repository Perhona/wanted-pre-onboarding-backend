package com.hiredhub.api.dto;

import lombok.Getter;

@Getter
public class JobPostingRequest {
    private final String position;
    private final String country;
    private final String region;
    private final Integer reward;
    private final String techStack;
    private final String jobDescription;
    private final Long companyId;

    public JobPostingRequest(String position, String country, String region, Integer reward, String techStack, String jobDescription, Long companyId) {
        this.position = position;
        this.country = country;
        this.region = region;
        this.reward = reward;
        this.techStack = techStack;
        this.jobDescription = jobDescription;
        this.companyId = companyId;
    }
}
