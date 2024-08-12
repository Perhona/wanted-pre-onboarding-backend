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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    private ApplicationResponse makeApplicationResponse(Application application) {
        return new ApplicationResponse(application.getId(), application.getUserId(), application.getJobPostingId());
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplication(Long id) {
        return makeApplicationResponse(applicationRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND)));
    }

    @Transactional
    public ApplicationResponse createApplication(ApplicationRequest applicationRequest) {
        User user = userRepository.findById(applicationRequest.userId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        JobPosting jobPosting = jobPostingRepository.findById(applicationRequest.jobPostingId()).orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        if (user.hasApplied(jobPosting)) {
            throw new CustomException(ErrorCode.APPLICATION_DUPLICATED);
        }

        return makeApplicationResponse(applicationRepository.save(new Application(user, jobPosting)));
    }
}
