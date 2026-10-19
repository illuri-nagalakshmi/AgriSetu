package com.agrisetu.service;

import com.agrisetu.dto.AdminStatsDto;
import com.agrisetu.dto.JobSummaryDto;
import com.agrisetu.dto.UserProfileDto;
import com.agrisetu.entity.*;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.repository.*;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AssignmentRepository assignmentRepository;
    private final ReportRepository reportRepository;
    private final AuditLogRepository auditLogRepository;
    private final UserService userService;
    private final JobService jobService;
    private final SecurityUtils securityUtils;

    public AdminService(UserRepository userRepository,
                        JobRepository jobRepository,
                        ApplicationRepository applicationRepository,
                        AssignmentRepository assignmentRepository,
                        ReportRepository reportRepository,
                        AuditLogRepository auditLogRepository,
                        UserService userService,
                        JobService jobService,
                        SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.assignmentRepository = assignmentRepository;
        this.reportRepository = reportRepository;
        this.auditLogRepository = auditLogRepository;
        this.userService = userService;
        this.jobService = jobService;
        this.securityUtils = securityUtils;
    }

    public AdminStatsDto getAdminStats() {
        long totalUsers = userRepository.count();
        long totalFarmers = userRepository.countByRole(Role.FARMER);
        long totalLabourers = userRepository.countByRole(Role.LABOURER);
        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.countByStatus(JobStatus.PUBLISHED);
        long totalApplications = applicationRepository.count();
        long totalAssignments = assignmentRepository.count();
        long pendingReports = reportRepository.countByStatus(ReportStatus.PENDING);

        return new AdminStatsDto(
                totalUsers, totalFarmers, totalLabourers,
                totalJobs, activeJobs, totalApplications,
                totalAssignments, pendingReports
        );
    }

    public List<UserProfileDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> userService.getUserProfileById(u.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void toggleUserStatus(Long userId, boolean activate, String reason) {
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (target.getRole() == Role.ADMIN) {
            throw new BadRequestException("Cannot suspend an Administrator account");
        }

        target.setActive(activate);
        userRepository.save(target);

        // Record Audit Log
        User admin = securityUtils.getCurrentUser();
        AuditLog audit = new AuditLog(
                admin,
                activate ? "REACTIVATE_USER" : "SUSPEND_USER",
                "User",
                target.getId(),
                "Reason: " + (reason != null ? reason : "Administrative action"),
                null
        );
        auditLogRepository.save(audit);
    }

    public List<JobSummaryDto> getAllJobsForModeration() {
        return jobRepository.findAll().stream()
                .map(jobService::mapToJobSummaryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void moderateJobStatus(Long jobId, JobStatus newStatus, String reason) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        job.setStatus(newStatus);
        jobRepository.save(job);

        // Record Audit Log
        User admin = securityUtils.getCurrentUser();
        AuditLog audit = new AuditLog(
                admin,
                "MODERATE_JOB_STATUS",
                "Job",
                job.getId(),
                "Changed status to " + newStatus + ". Reason: " + reason,
                null
        );
        auditLogRepository.save(audit);
    }

    public String exportUsersCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append("User ID,Full Name,Email,Role,Village,District,State,Language,Active,Created At\n");
        for (User u : userRepository.findAll()) {
            csv.append(u.getId()).append(",")
               .append("\"").append(u.getFullName().replace("\"", "\"\"")).append("\",")
               .append("\"").append(u.getEmail()).append("\",")
               .append(u.getRole()).append(",")
               .append("\"").append(u.getVillage()).append("\",")
               .append("\"").append(u.getDistrict()).append("\",")
               .append("\"").append(u.getState()).append("\",")
               .append(u.getPreferredLanguage()).append(",")
               .append(u.isActive()).append(",")
               .append(u.getCreatedAt()).append("\n");
        }
        return csv.toString();
    }

    public String exportJobsCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append("Job ID,Title,Category,Farmer,District,State,Wage,Wage Basis,Required,Confirmed,Vacancies,Status,Created At\n");
        for (Job j : jobRepository.findAll()) {
            csv.append(j.getId()).append(",")
               .append("\"").append(j.getTitle().replace("\"", "\"\"")).append("\",")
               .append("\"").append(j.getCategory()).append("\",")
               .append("\"").append(j.getFarmer().getFullName().replace("\"", "\"\"")).append("\",")
               .append("\"").append(j.getDistrict()).append("\",")
               .append("\"").append(j.getState()).append("\",")
               .append(j.getWageAmount()).append(",")
               .append(j.getWageBasis()).append(",")
               .append(j.getRequiredWorkers()).append(",")
               .append(j.getConfirmedWorkers()).append(",")
               .append(j.getRemainingVacancies()).append(",")
               .append(j.getStatus()).append(",")
               .append(j.getCreatedAt()).append("\n");
        }
        return csv.toString();
    }
}
