package com.hiredhub.api.service;

import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.dto.JobPostingResponse;
import com.hiredhub.api.dto.JobPostingSearchRequest;
import com.hiredhub.api.exception.CustomException;
import com.hiredhub.api.exception.ErrorCode;
import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import com.hiredhub.api.repository.CompanyRepository;
import com.hiredhub.api.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final CompanyRepository companyRepository;
    private final JobPostingRepository jobPostingRepository;

    private JobPostingResponse.DetailResponse makeJobPostingDetailResponse(JobPosting jobPosting) {
        return new JobPostingResponse.DetailResponse(jobPosting.getId(), jobPosting.getCompany().getName(), jobPosting.getPosition(), jobPosting.getCountry(), jobPosting.getRegion(), jobPosting.getReward(), jobPosting.getTechStack(), jobPosting.getJobDescription(), jobPosting.getCompany().getOtherJobPostingIds(jobPosting.getId()));
    }

    private List<JobPostingResponse.ListResponse> makeJobPostingListResponse(List<JobPosting> jobPostings) {
        return jobPostings.stream().map(jobPosting -> new JobPostingResponse.ListResponse(jobPosting.getId(), jobPosting.getCompany().getName(), jobPosting.getPosition(), jobPosting.getCountry(), jobPosting.getRegion(), jobPosting.getReward(), jobPosting.getTechStack())).collect(Collectors.toList());
    }

    @Transactional
    public JobPostingResponse.DetailResponse createJobPosting(JobPostingRequest.CreateRequest jobPostingCreateRequest) {
        Company company = companyRepository.findById(jobPostingCreateRequest.companyId()).orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        return makeJobPostingDetailResponse(jobPostingRepository.save(new JobPosting(jobPostingCreateRequest.position(), jobPostingCreateRequest.country(), jobPostingCreateRequest.region(), jobPostingCreateRequest.reward(), jobPostingCreateRequest.techStack(), jobPostingCreateRequest.jobDescription(), company)));
    }

    @Transactional(readOnly = true)
    public List<JobPostingResponse.ListResponse> listAllJobPostings() {
        return makeJobPostingListResponse(jobPostingRepository.findAll());
    }

    @Transactional(readOnly = true)
    public JobPostingResponse.DetailResponse getJobPosting(Long id) {
        return makeJobPostingDetailResponse(jobPostingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND)));
    }

    @Transactional
    public JobPostingResponse.DetailResponse updateJobPosting(Long id, JobPostingRequest.UpdateRequest jobPostingUpdateRequest) {
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        jobPosting.updatePosition(jobPostingUpdateRequest.position());
        jobPosting.updateReward(jobPostingUpdateRequest.reward());
        jobPosting.updateTechStack(jobPostingUpdateRequest.techStack());
        jobPosting.updateJobDescription(jobPostingUpdateRequest.jobDescription());

        return makeJobPostingDetailResponse(jobPosting);
    }

    @Transactional
    public void deleteJobPosting(Long id) {
        jobPostingRepository.delete(jobPostingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public List<JobPostingResponse.ListResponse> searchJobPostings(JobPostingSearchRequest jobPostingSearchRequest) {
        if (jobPostingSearchRequest.hasAllParams()) {
            return makeJobPostingListResponse(jobPostingRepository.findJobPostingsByCompanyNameContainingIgnoreCaseAndPositionContainingIgnoreCase(jobPostingSearchRequest.companyName(), jobPostingSearchRequest.position()));
        }
        if (jobPostingSearchRequest.hasCompanyName()) {
            return makeJobPostingListResponse(jobPostingRepository.findJobPostingsByCompanyNameContainingIgnoreCase(jobPostingSearchRequest.companyName()));
        }
        if (jobPostingSearchRequest.hasPosition()) {
            return makeJobPostingListResponse(jobPostingRepository.findJobPostingsByPositionContainingIgnoreCase(jobPostingSearchRequest.position()));
        }
        return makeJobPostingListResponse(jobPostingRepository.findAll());
    }
}
