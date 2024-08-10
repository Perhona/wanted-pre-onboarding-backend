package com.hiredhub.api.dto;

public class JobPostingRequest {
    public record CreateRequest(String position,
                                String country,
                                String region,
                                Integer reward,
                                String techStack,
                                String jobDescription,
                                Long companyId) {
    }

    public record UpdateRequest(String position,
                                Integer reward,
                                String techStack,
                                String jobDescription) {
    }
}
