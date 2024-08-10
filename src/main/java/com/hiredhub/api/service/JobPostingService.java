package com.hiredhub.api.service;

import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.dto.JobPostingResponse;
import com.hiredhub.api.exception.CustomException;
import com.hiredhub.api.exception.ErrorCode;
import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import com.hiredhub.api.repository.CompanyRepository;
import com.hiredhub.api.repository.JobPostingRepository;
import jakarta.transaction.Transactional;
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

    private JobPostingResponse.DetailResponse createJobPostingDetailResponse(JobPosting jobPosting){
        return new JobPostingResponse.DetailResponse(jobPosting.getId(), jobPosting.getCompany().getName(), jobPosting.getPosition(), jobPosting.getCountry(), jobPosting.getRegion(), jobPosting.getReward(), jobPosting.getTechStack(), jobPosting.getJobDescription(), jobPosting.getCompany().getOtherJobPostingIds(jobPosting.getId()));
    }

    private JobPostingResponse.ListResponse createJobPostingListResponse(JobPosting jobPosting){
        return new JobPostingResponse.ListResponse(jobPosting.getId(), jobPosting.getCompany().getName(), jobPosting.getPosition(), jobPosting.getCountry(), jobPosting.getRegion(), jobPosting.getReward(), jobPosting.getTechStack());
    }

    @Transactional
    public JobPostingResponse.DetailResponse createJobPosting(JobPostingRequest jobPostingRequest) {
        Company company = companyRepository.findById(jobPostingRequest.getCompanyId()).orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
        JobPosting jobPosting = new JobPosting(jobPostingRequest.getPosition(), jobPostingRequest.getCountry(), jobPostingRequest.getRegion(), jobPostingRequest.getReward(), jobPostingRequest.getTechStack(), jobPostingRequest.getJobDescription(), company);

        jobPostingRepository.save(jobPosting);
        return createJobPostingDetailResponse(jobPosting);
    }

    public List<JobPostingResponse.ListResponse> listAllJobPostings() {
        return jobPostingRepository.findAll().stream().map(this::createJobPostingListResponse).toList();
    }

    public JobPostingResponse.DetailResponse getJobPosting(Long id) {
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));
        return createJobPostingDetailResponse(jobPosting);
    }

    @Transactional
    public JobPostingResponse.DetailResponse updateJobPosting(Long id, JobPostingRequest.UpdateRequest jobPostingUpdateRequest) {
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        jobPosting.updatePosition(jobPostingUpdateRequest.position());
        jobPosting.updateReward(jobPostingUpdateRequest.reward());
        jobPosting.updateTechStack(jobPostingUpdateRequest.techStack());
        jobPosting.updateJobDescription(jobPostingUpdateRequest.jobDescription());

        return createJobPostingDetailResponse(jobPosting);
    }
}
