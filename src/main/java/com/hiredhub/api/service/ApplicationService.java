package com.hiredhub.api.service;

import com.hiredhub.api.dto.ApplicationRequest;
import com.hiredhub.api.dto.ApplicationResponse;
import com.hiredhub.api.exception.CustomException;
import com.hiredhub.api.exception.ErrorCode;
import com.hiredhub.api.model.Application;
import com.hiredhub.api.model.JobPosting;
import com.hiredhub.api.model.User;
import com.hiredhub.api.repository.ApplicationRepository;
import com.hiredhub.api.repository.JobPostingRepository;
import com.hiredhub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    private ApplicationResponse createApplicationResponse(Application application) {
        return new ApplicationResponse(application.getId(), application.getUserId(), application.getJobPostingId());
    }

    public ApplicationResponse getApplication(Long id) {
        return createApplicationResponse(applicationRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND)));
    }

    public ApplicationResponse createApplication(ApplicationRequest applicationRequest) {
        User user = userRepository.findById(applicationRequest.userId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        JobPosting jobPosting = jobPostingRepository.findById(applicationRequest.jobPostingId()).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        if (user.hasApplied(jobPosting)) {
            throw new CustomException(ErrorCode.APPLICATION_DUPLICATED);
        }

        return createApplicationResponse(applicationRepository.save(new Application(user, jobPosting)));
    }
}
