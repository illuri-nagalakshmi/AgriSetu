package com.agrisetu.controller;

import com.agrisetu.dto.ApiResponse;
import com.agrisetu.dto.ApplicationDecisionRequest;
import com.agrisetu.dto.ApplicationDto;
import com.agrisetu.dto.ApplicationRequest;
import com.agrisetu.service.WorkerAllocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final WorkerAllocationService workerAllocationService;

    public ApplicationController(WorkerAllocationService workerAllocationService) {
        this.workerAllocationService = workerAllocationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<ApplicationDto>> applyForJob(@Valid @RequestBody ApplicationRequest request) {
        ApplicationDto application = workerAllocationService.applyForJob(request);
        return ResponseEntity.ok(ApiResponse.success("Application submitted successfully", application));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getMyApplications() {
        List<ApplicationDto> list = workerAllocationService.getMyApplications();
        return ResponseEntity.ok(ApiResponse.success("Applications retrieved", list));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getApplicationsForJob(@PathVariable Long jobId) {
        List<ApplicationDto> list = workerAllocationService.getApplicationsForJob(jobId);
        return ResponseEntity.ok(ApiResponse.success("Job applications retrieved", list));
    }

    @PostMapping("/{id}/decision")
    public ResponseEntity<ApiResponse<ApplicationDto>> decideApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationDecisionRequest request) {
        ApplicationDto decided = workerAllocationService.decideApplication(id, request);
        return ResponseEntity.ok(ApiResponse.success("Application decided successfully", decided));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdrawApplication(@PathVariable Long id) {
        workerAllocationService.withdrawApplication(id);
        return ResponseEntity.ok(ApiResponse.success("Application withdrawn successfully"));
    }
}
