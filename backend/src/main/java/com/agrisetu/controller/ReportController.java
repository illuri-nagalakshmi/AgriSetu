package com.agrisetu.controller;

import com.agrisetu.dto.ApiResponse;
import com.agrisetu.dto.ReportCreateRequest;
import com.agrisetu.dto.ReportDto;
import com.agrisetu.entity.ReportStatus;
import com.agrisetu.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReportDto>> submitReport(@Valid @RequestBody ReportCreateRequest request) {
        ReportDto report = reportService.createReport(request);
        return ResponseEntity.ok(ApiResponse.success("Report submitted successfully. Administrators will review it.", report));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getAllReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved", reports));
    }

    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ReportDto>> resolveReport(
            @PathVariable Long id,
            @RequestParam(required = false) String adminNotes,
            @RequestParam(defaultValue = "RESOLVED") ReportStatus status) {
        ReportDto resolved = reportService.resolveReport(id, adminNotes, status);
        return ResponseEntity.ok(ApiResponse.success("Report marked as " + status, resolved));
    }
}
