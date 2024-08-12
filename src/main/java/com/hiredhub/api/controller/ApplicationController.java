package com.hiredhub.api.controller;

import com.hiredhub.api.dto.ApplicationRequest;
import com.hiredhub.api.dto.ApplicationResponse;
import com.hiredhub.api.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/applications/{id}")
    public ResponseEntity<ApplicationResponse> getApplication(@PathVariable Long id) {
        ApplicationResponse applicationResponse = applicationService.getApplication(id);
        return ResponseEntity.ok().body(applicationResponse);
    }

    @PostMapping("/applications")
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse applicationResponse = applicationService.createApplication(applicationRequest);
        return ResponseEntity.created(URI.create("/applications/" + applicationResponse.id())).body(applicationResponse);
    }
}
