package com.hiredhub.api.controller;

import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.dto.JobPostingResponse;
import com.hiredhub.api.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @GetMapping("/jobPostings")
    public ResponseEntity<List<JobPostingResponse.ListResponse>> listJobPostings() {
        return ResponseEntity.ok().body(jobPostingService.listAllJobPostings());
    }

    @GetMapping("/jobPostings/{id}")
    public ResponseEntity<JobPostingResponse.DetailResponse> getJobPosting(@PathVariable Long id) {
        return ResponseEntity.ok().body(jobPostingService.getJobPosting(id));
    }

    @PostMapping("/jobPostings")
    public ResponseEntity<JobPostingResponse.DetailResponse> createJobPosting(@RequestBody JobPostingRequest jobPostingRequest) {
        JobPostingResponse.DetailResponse jobPostingDetailResponse = jobPostingService.createJobPosting(jobPostingRequest);
        return ResponseEntity.created(URI.create("/jobPostings/" + jobPostingDetailResponse.id())).body(jobPostingDetailResponse);
    }
}
