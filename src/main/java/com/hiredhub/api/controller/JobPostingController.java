package com.hiredhub.api.controller;

import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.dto.JobPostingResponse;
import com.hiredhub.api.dto.JobPostingSearchRequest;
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
    public ResponseEntity<JobPostingResponse.DetailResponse> createJobPosting(@RequestBody JobPostingRequest.CreateRequest jobPostingCreateRequest) {
        JobPostingResponse.DetailResponse jobPostingDetailResponse = jobPostingService.createJobPosting(jobPostingCreateRequest);
        return ResponseEntity.created(URI.create("/jobPostings/" + jobPostingDetailResponse.id())).body(jobPostingDetailResponse);
    }

    @PutMapping("/jobPostings/{id}")
    public ResponseEntity<JobPostingResponse.DetailResponse> updateJobPosting(@PathVariable Long id, @RequestBody JobPostingRequest.UpdateRequest jobPostingUpdateRequest) {
        return ResponseEntity.ok().body(jobPostingService.updateJobPosting(id, jobPostingUpdateRequest));
    }

    @DeleteMapping("/jobPostings/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable Long id) {
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jobPostings/search")
    public ResponseEntity<List<JobPostingResponse.ListResponse>> searchJobPostings(@ModelAttribute JobPostingSearchRequest jobPostingSearchRequest) {
        return ResponseEntity.ok().body(jobPostingService.searchJobPostings(jobPostingSearchRequest));
    }
}
