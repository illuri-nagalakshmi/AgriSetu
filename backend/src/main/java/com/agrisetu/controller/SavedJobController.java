package com.agrisetu.controller;

import com.agrisetu.dto.ApiResponse;
import com.agrisetu.dto.JobSummaryDto;
import com.agrisetu.entity.Job;
import com.agrisetu.entity.SavedJob;
import com.agrisetu.entity.User;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.repository.JobRepository;
import com.agrisetu.repository.SavedJobRepository;
import com.agrisetu.security.SecurityUtils;
import com.agrisetu.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final JobService jobService;
    private final SecurityUtils securityUtils;

    public SavedJobController(SavedJobRepository savedJobRepository,
                              JobRepository jobRepository,
                              JobService jobService,
                              SecurityUtils securityUtils) {
        this.savedJobRepository = savedJobRepository;
        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/{jobId}")
    public ResponseEntity<ApiResponse<Void>> saveJob(@PathVariable Long jobId) {
        User labourer = securityUtils.getCurrentUser();
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!savedJobRepository.existsByLabourerIdAndJobId(labourer.getId(), job.getId())) {
            savedJobRepository.save(new SavedJob(labourer, job));
        }
        return ResponseEntity.ok(ApiResponse.success("Job saved successfully"));
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<ApiResponse<Void>> removeSavedJob(@PathVariable Long jobId) {
        User labourer = securityUtils.getCurrentUser();
        savedJobRepository.findByLabourerIdAndJobId(labourer.getId(), jobId).ifPresent(savedJobRepository::delete);
        return ResponseEntity.ok(ApiResponse.success("Job removed from saved list"));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<JobSummaryDto>>> getMySavedJobs() {
        User labourer = securityUtils.getCurrentUser();
        List<JobSummaryDto> list = savedJobRepository.findByLabourerIdOrderBySavedAtDesc(labourer.getId()).stream()
                .map(sj -> {
                    JobSummaryDto dto = jobService.mapToJobSummaryDto(sj.getJob());
                    dto.setSaved(true);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Saved jobs retrieved", list));
    }
}
