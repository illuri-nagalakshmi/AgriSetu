package com.agrisetu.controller;

import com.agrisetu.dto.*;
import com.agrisetu.entity.JobStatus;
import com.agrisetu.entity.ReportStatus;
import com.agrisetu.service.AdminService;
import com.agrisetu.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final ReportService reportService;

    public AdminController(AdminService adminService, ReportService reportService) {
        this.adminService = adminService;
        this.reportService = reportService;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsDto>> getAdminStats() {
        AdminStatsDto stats = adminService.getAdminStats();
        return ResponseEntity.ok(ApiResponse.success("Admin statistics retrieved", stats));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserProfileDto>>> getAllUsers() {
        List<UserProfileDto> users = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("All users retrieved", users));
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<Void>> toggleUserStatus(
            @PathVariable Long id,
            @RequestParam boolean activate,
            @RequestParam(required = false) String reason) {
        adminService.toggleUserStatus(id, activate, reason);
        return ResponseEntity.ok(ApiResponse.success("User account status updated"));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<JobSummaryDto>>> getAllJobs() {
        List<JobSummaryDto> jobs = adminService.getAllJobsForModeration();
        return ResponseEntity.ok(ApiResponse.success("All jobs retrieved", jobs));
    }

    @PutMapping("/jobs/{id}/status")
    public ResponseEntity<ApiResponse<Void>> moderateJob(
            @PathVariable Long id,
            @RequestParam JobStatus status,
            @RequestParam(required = false) String reason) {
        adminService.moderateJobStatus(id, status, reason);
        return ResponseEntity.ok(ApiResponse.success("Job status updated by administrator"));
    }

    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved", reports));
    }

    @PutMapping("/reports/{id}/resolve")
    public ResponseEntity<ApiResponse<ReportDto>> resolveReport(
            @PathVariable Long id,
            @RequestParam(required = false) String adminNotes,
            @RequestParam(defaultValue = "RESOLVED") ReportStatus status) {
        ReportDto resolved = reportService.resolveReport(id, adminNotes, status);
        return ResponseEntity.ok(ApiResponse.success("Report resolved", resolved));
    }

    @GetMapping("/export/users-csv")
    public ResponseEntity<String> exportUsersCsv() {
        String csv = adminService.exportUsersCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"agrisetu-users.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csv);
    }

    @GetMapping("/export/jobs-csv")
    public ResponseEntity<String> exportJobsCsv() {
        String csv = adminService.exportJobsCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"agrisetu-jobs.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csv);
    }
}
