package com.hiredhub.api.dto;

import java.util.List;

public class JobPostingResponse {
    public record DetailResponse(Long id,
                                 String companyName,
                                 String position,
                                 String country,
                                 String region,
                                 Integer reward,
                                 String techStack,
                                 String jobDescription,
                                 List<Long> companyOtherJobPostingIds) {
    }

    public record ListResponse(Long id,
                               String companyName,
                               String position,
                               String country,
                               String region,
                               Integer reward,
                               String techStack) {
    }
}
