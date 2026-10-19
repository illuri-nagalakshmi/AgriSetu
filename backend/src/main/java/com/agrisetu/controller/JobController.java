package com.agrisetu.controller;

import com.agrisetu.dto.*;
import com.agrisetu.entity.JobStatus;
import com.agrisetu.service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobDetailDto>> createJob(@Valid @RequestBody JobCreateRequest request) {
        JobDetailDto created = jobService.createJob(request);
        return ResponseEntity.ok(ApiResponse.success("Job posted successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<JobSummaryDto>>> searchJobs(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) BigDecimal minWage,
            @RequestParam(required = false) BigDecimal maxWage,
            @RequestParam(required = false) Boolean mealsProvided,
            @RequestParam(required = false) Boolean accommodationProvided,
            @RequestParam(defaultValue = "newest") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<JobSummaryDto> results = jobService.searchJobs(
                category, district, state,
                minWage, maxWage,
                mealsProvided, accommodationProvided,
                sortBy, page, size
        );
        return ResponseEntity.ok(ApiResponse.success("Jobs retrieved", results));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<JobSummaryDto>>> getFeaturedJobs() {
        List<JobSummaryDto> list = jobService.getFeaturedJobs();
        return ResponseEntity.ok(ApiResponse.success("Featured jobs retrieved", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDetailDto>> getJobDetails(@PathVariable Long id) {
        JobDetailDto details = jobService.getJobDetails(id);
        return ResponseEntity.ok(ApiResponse.success("Job details retrieved", details));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDetailDto>> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobUpdateRequest request) {
        JobDetailDto updated = jobService.updateJob(id, request);
        return ResponseEntity.ok(ApiResponse.success("Job updated successfully", updated));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobSummaryDto>> updateJobStatus(
            @PathVariable Long id,
            @RequestParam JobStatus status) {
        JobSummaryDto updated = jobService.updateJobStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Job status updated to " + status, updated));
    }

    @GetMapping("/farmer/my-jobs")
    public ResponseEntity<ApiResponse<List<JobSummaryDto>>> getMyJobs() {
        List<JobSummaryDto> jobs = jobService.getMyJobs();
        return ResponseEntity.ok(ApiResponse.success("Farmer jobs retrieved", jobs));
    }
}
