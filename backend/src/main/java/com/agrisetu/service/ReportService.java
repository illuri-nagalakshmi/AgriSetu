package com.agrisetu.service;

import com.agrisetu.dto.ReportCreateRequest;
import com.agrisetu.dto.ReportDto;
import com.agrisetu.entity.Job;
import com.agrisetu.entity.Report;
import com.agrisetu.entity.ReportStatus;
import com.agrisetu.entity.User;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.repository.JobRepository;
import com.agrisetu.repository.ReportRepository;
import com.agrisetu.repository.UserRepository;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final SecurityUtils securityUtils;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         JobRepository jobRepository,
                         SecurityUtils securityUtils) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.securityUtils = securityUtils;
    }

    @Transactional
    public ReportDto createReport(ReportCreateRequest request) {
        User reporter = securityUtils.getCurrentUser();

        User reportedUser = null;
        if (request.getReportedUserId() != null) {
            reportedUser = userRepository.findById(request.getReportedUserId()).orElse(null);
        }

        Job job = null;
        if (request.getJobId() != null) {
            job = jobRepository.findById(request.getJobId()).orElse(null);
        }

        Report report = new Report(
                reporter,
                reportedUser,
                job,
                request.getReason().trim(),
                request.getDetails().trim()
        );
        report = reportRepository.save(report);
        return mapToDto(report);
    }

    public List<ReportDto> getAllReports() {
        return reportRepository.findByOrderByCreatedAtDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReportDto resolveReport(Long reportId, String adminNotes, ReportStatus status) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + reportId));

        report.setStatus(status);
        report.setAdminNotes(adminNotes != null ? adminNotes.trim() : null);
        report.setResolvedAt(LocalDateTime.now());
        report = reportRepository.save(report);
        return mapToDto(report);
    }

    public ReportDto mapToDto(Report r) {
        ReportDto dto = new ReportDto();
        dto.setId(r.getId());
        dto.setReporterId(r.getReporter().getId());
        dto.setReporterName(r.getReporter().getFullName());
        if (r.getReportedUser() != null) {
            dto.setReportedUserId(r.getReportedUser().getId());
            dto.setReportedUserName(r.getReportedUser().getFullName());
        }
        if (r.getJob() != null) {
            dto.setJobId(r.getJob().getId());
            dto.setJobTitle(r.getJob().getTitle());
        }
        dto.setReason(r.getReason());
        dto.setDetails(r.getDetails());
        dto.setStatus(r.getStatus());
        dto.setAdminNotes(r.getAdminNotes());
        dto.setCreatedAt(r.getCreatedAt());
        dto.setResolvedAt(r.getResolvedAt());
        return dto;
    }
}
