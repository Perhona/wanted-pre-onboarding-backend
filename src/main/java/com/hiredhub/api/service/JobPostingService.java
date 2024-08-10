package com.hiredhub.api.service;

import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.dto.JobPostingResponse;
import com.hiredhub.api.exception.CustomException;
import com.hiredhub.api.exception.ErrorCode;
import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import com.hiredhub.api.repository.CompanyRepository;
import com.hiredhub.api.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final CompanyRepository companyRepository;
    private final JobPostingRepository jobPostingRepository;

    private JobPostingResponse createJobPostingResponse(JobPosting jobPosting) {
        return new JobPostingResponse(jobPosting.getId(), jobPosting.getCompany().getName(), jobPosting.getPosition(), jobPosting.getCountry(), jobPosting.getRegion(), jobPosting.getReward(), jobPosting.getTechStack(), jobPosting.getJobDescription());
    }

    public JobPostingResponse createJobPosting(JobPostingRequest jobPostingRequest) {
        Company company = companyRepository.findById(jobPostingRequest.getCompanyId()).orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
        JobPosting jobPosting = new JobPosting(jobPostingRequest.getPosition(), jobPostingRequest.getCountry(), jobPostingRequest.getRegion(), jobPostingRequest.getReward(), jobPostingRequest.getTechStack(), jobPostingRequest.getJobDescription(), company);

        jobPostingRepository.save(jobPosting);
        return createJobPostingResponse(jobPosting);
    }

    public List<JobPostingResponse> listAllJobPostings() {
        return jobPostingRepository.findAll().stream().map(this::createJobPostingResponse).toList();
    }

    public JobPostingResponse.JobPostingDetailResponse getJobPosting(Long id) {
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));
        Company company = jobPosting.getCompany();
        return new JobPostingResponse.JobPostingDetailResponse(jobPosting.getId(), jobPosting.getCompany().getName(), jobPosting.getPosition(), jobPosting.getCountry(), jobPosting.getRegion(), jobPosting.getReward(), jobPosting.getTechStack(), jobPosting.getJobDescription(), company.getOtherJobPostingIds(jobPosting.getId()));
    }
}
