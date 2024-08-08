package com.hiredhub.api.dto;

import lombok.Getter;

@Getter
public class JobPostingResponse {
    private final Long id;
    private final String companyName;
    private final String position;
    private final String country;
    private final String region;
    private final Integer reward;
    private final String techStack;
    private final String jobDescription;

    public JobPostingResponse(Long id, String companyName, String position, String country, String region, Integer reward, String techStack, String jobDescription) {
        this.id = id;
        this.companyName = companyName;
        this.position = position;
        this.country = country;
        this.region = region;
        this.reward = reward;
        this.techStack = techStack;
        this.jobDescription = jobDescription;
    }
}
