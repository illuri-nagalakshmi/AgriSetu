package com.agrisetu.service;

import com.agrisetu.dto.ApplicationDecisionRequest;
import com.agrisetu.dto.ApplicationDto;
import com.agrisetu.dto.ApplicationRequest;
import com.agrisetu.dto.AssignmentDto;
import com.agrisetu.entity.*;
import com.agrisetu.exception.AllocationConflictException;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.exception.UnauthorizedException;
import com.agrisetu.repository.ApplicationRepository;
import com.agrisetu.repository.AssignmentRepository;
import com.agrisetu.repository.JobRepository;
import com.agrisetu.repository.LabourerProfileRepository;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerAllocationService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AssignmentRepository assignmentRepository;
    private final LabourerProfileRepository labourerProfileRepository;
    private final NotificationService notificationService;
    private final SecurityUtils securityUtils;
    private final UserService userService;

    public WorkerAllocationService(JobRepository jobRepository,
                                  ApplicationRepository applicationRepository,
                                  AssignmentRepository assignmentRepository,
                                  LabourerProfileRepository labourerProfileRepository,
                                  NotificationService notificationService,
                                  SecurityUtils securityUtils,
                                  UserService userService) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.assignmentRepository = assignmentRepository;
        this.labourerProfileRepository = labourerProfileRepository;
        this.notificationService = notificationService;
        this.securityUtils = securityUtils;
        this.userService = userService;
    }

    /**
     * Submit an application for an open job.
     */
    @Transactional
    public ApplicationDto applyForJob(ApplicationRequest request) {
        User labourer = securityUtils.getCurrentUser();
        if (labourer.getRole() != Role.LABOURER && labourer.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only registered labourers can apply for jobs");
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + request.getJobId()));

        if (job.getStatus() != JobStatus.PUBLISHED) {
            throw new BadRequestException("Applications are closed for this job (Status: " + job.getStatus() + ")");
        }

        if (LocalDate.now().isAfter(job.getApplicationDeadline())) {
            throw new BadRequestException("The application deadline (" + job.getApplicationDeadline() + ") has passed");
        }

        if (job.getRemainingVacancies() <= 0) {
            throw new BadRequestException("This job is already fully staffed (0 vacancies remaining)");
        }

        if (applicationRepository.existsByJobIdAndLabourerId(job.getId(), labourer.getId())) {
            throw new BadRequestException("You have already submitted an application for this job");
        }

        Application application = new Application(job, labourer, request.getCoverNote());
        application = applicationRepository.save(application);

        // Notify Farmer
        notificationService.createNotification(
                job.getFarmer(),
                "NEW_APPLICATION",
                "New Application for " + job.getTitle(),
                labourer.getFullName() + " applied for your job: " + job.getTitle(),
                "APPLICATION",
                application.getId()
        );

        return mapToApplicationDto(application, false);
    }

    /**
     * Critical Worker Allocation Logic.
     * Uses pessimistic write lock on the Job to prevent race conditions when multiple
     * farmers or simultaneous requests attempt to allocate workers.
     * Enforces: Remaining vacancies = Required workers - Confirmed workers
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ApplicationDto decideApplication(Long applicationId, ApplicationDecisionRequest request) {
        User currentUser = securityUtils.getCurrentUser();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        final Long targetJobId = application.getJob().getId();

        // Lock the Job row with SELECT ... FOR UPDATE (Pessimistic Write Lock)
        Job job = jobRepository.findByIdWithPessimisticLock(targetJobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + targetJobId));

        if (!job.getFarmer().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only the farmer who posted this job can decide applications");
        }

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new BadRequestException("This application has already been decided (Status: " + application.getStatus() + ")");
        }

        if (request.getStatus() == ApplicationStatus.ACCEPTED) {
            // Strict capacity validation
            if (job.getConfirmedWorkers() >= job.getRequiredWorkers() || job.getRemainingVacancies() <= 0) {
                throw new AllocationConflictException(
                        "Cannot accept applicant: Job capacity reached (" + job.getConfirmedWorkers() + 
                        "/" + job.getRequiredWorkers() + " confirmed, 0 vacancies remaining)."
                );
            }

            // Allocation calculation
            int newConfirmed = job.getConfirmedWorkers() + 1;
            int newVacancies = job.getRequiredWorkers() - newConfirmed;

            job.setConfirmedWorkers(newConfirmed);
            job.setRemainingVacancies(newVacancies);
            jobRepository.save(job);

            application.setStatus(ApplicationStatus.ACCEPTED);
            application.setDecidedAt(LocalDateTime.now());
            application.setDecisionReason(request.getDecisionReason());
            application = applicationRepository.save(application);

            // Create Confirmed Assignment
            Assignment assignment = new Assignment(
                    job,
                    application.getLabourer(),
                    application,
                    job.getWageAmount(),
                    job.getWageBasis().name()
            );
            assignmentRepository.save(assignment);

            // Notify Labourer
            notificationService.createNotification(
                    application.getLabourer(),
                    "APPLICATION_ACCEPTED",
                    "Application Accepted!",
                    "Farmer " + job.getFarmer().getFullName() + " accepted your application for '" + job.getTitle() + 
                    "'. You can now view farm contact details.",
                    "ASSIGNMENT",
                    assignment.getId()
            );

            return mapToApplicationDto(application, true);

        } else if (request.getStatus() == ApplicationStatus.REJECTED) {
            application.setStatus(ApplicationStatus.REJECTED);
            application.setDecidedAt(LocalDateTime.now());
            application.setDecisionReason(request.getDecisionReason());
            application = applicationRepository.save(application);

            notificationService.createNotification(
                    application.getLabourer(),
                    "APPLICATION_REJECTED",
                    "Application Update",
                    "Your application for '" + job.getTitle() + "' was not accepted at this time.",
                    "JOB",
                    job.getId()
            );

            return mapToApplicationDto(application, false);
        } else {
            throw new BadRequestException("Invalid decision status. Must be ACCEPTED or REJECTED");
        }
    }

    /**
     * Labourer withdraws a pending application.
     */
    @Transactional
    public void withdrawApplication(Long applicationId) {
        User labourer = securityUtils.getCurrentUser();
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (!application.getLabourer().getId().equals(labourer.getId())) {
            throw new UnauthorizedException("You can only withdraw your own applications");
        }

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new BadRequestException("Only pending applications can be withdrawn");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setDecidedAt(LocalDateTime.now());
        applicationRepository.save(application);
    }

    /**
     * Release vacancy when an eligible assignment is cancelled.
     */
    @Transactional
    public AssignmentDto cancelAssignment(Long assignmentId, String reason) {
        User currentUser = securityUtils.getCurrentUser();
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));

        boolean isFarmer = assignment.getJob().getFarmer().getId().equals(currentUser.getId());
        boolean isLabourer = assignment.getLabourer().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isFarmer && !isLabourer && !isAdmin) {
            throw new UnauthorizedException("You are not authorized to cancel this assignment");
        }

        if (assignment.getStatus() == AssignmentStatus.COMPLETED) {
            throw new BadRequestException("Completed assignments cannot be cancelled");
        }

        if (assignment.getStatus() == AssignmentStatus.CANCELLED) {
            throw new BadRequestException("Assignment has already been cancelled");
        }

        // Lock Job row to safely release vacancy
        Job job = jobRepository.findByIdWithPessimisticLock(assignment.getJob().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (job.getConfirmedWorkers() > 0) {
            int newConfirmed = job.getConfirmedWorkers() - 1;
            job.setConfirmedWorkers(newConfirmed);
            job.setRemainingVacancies(job.getRequiredWorkers() - newConfirmed);
            jobRepository.save(job);
        }

        assignment.setStatus(AssignmentStatus.CANCELLED);
        assignment.setCancellationReason(reason != null ? reason.trim() : "Cancelled by participant");
        assignment = assignmentRepository.save(assignment);

        // Notify other party
        User notifyTarget = isFarmer ? assignment.getLabourer() : job.getFarmer();
        notificationService.createNotification(
                notifyTarget,
                "ASSIGNMENT_CANCELLED",
                "Assignment Cancelled",
                "Assignment for '" + job.getTitle() + "' was cancelled. Reason: " + assignment.getCancellationReason(),
                "JOB",
                job.getId()
        );

        return mapToAssignmentDto(assignment);
    }

    @Transactional(readOnly = true)
    public List<ApplicationDto> getApplicationsForJob(Long jobId) {
        User currentUser = securityUtils.getCurrentUser();
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getFarmer().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to view applications for this job");
        }

        return applicationRepository.findByJobIdOrderByAppliedAtDesc(jobId).stream()
                .map(app -> mapToApplicationDto(app, app.getStatus() == ApplicationStatus.ACCEPTED))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApplicationDto> getMyApplications() {
        User labourer = securityUtils.getCurrentUser();
        return applicationRepository.findByLabourerIdOrderByAppliedAtDesc(labourer.getId()).stream()
                .map(app -> mapToApplicationDto(app, false))
                .collect(Collectors.toList());
    }

    public ApplicationDto mapToApplicationDto(Application app, boolean revealContact) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(app.getId());
        dto.setJobId(app.getJob().getId());
        dto.setJobTitle(app.getJob().getTitle());
        dto.setJobCategory(app.getJob().getCategory());
        dto.setLabourerId(app.getLabourer().getId());
        dto.setLabourerName(app.getLabourer().getFullName());
        dto.setStatus(app.getStatus());
        dto.setCoverNote(app.getCoverNote());
        dto.setAppliedAt(app.getAppliedAt());
        dto.setDecidedAt(app.getDecidedAt());
        dto.setDecisionReason(app.getDecisionReason());

        // Contact privacy: Reveal phone only if accepted or authorized assignment exists
        boolean canReveal = revealContact || userService.canRevealContact(app.getLabourer().getId());
        dto.setContactVisible(canReveal);
        if (canReveal) {
            dto.setLabourerPhone(app.getLabourer().getPhoneNumber());
        } else {
            dto.setLabourerPhone(userService.maskPhoneNumber(app.getLabourer().getPhoneNumber()));
        }

        labourerProfileRepository.findByUserId(app.getLabourer().getId()).ifPresent(lp -> {
            dto.setLabourerSkills(lp.getSkills());
            dto.setLabourerExperienceYears(lp.getExperienceYears());
        });

        return dto;
    }

    public AssignmentDto mapToAssignmentDto(Assignment assign) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(assign.getId());
        dto.setJobId(assign.getJob().getId());
        dto.setJobTitle(assign.getJob().getTitle());
        dto.setFarmLocation(assign.getJob().getFarmLocation());
        dto.setStartDate(assign.getJob().getStartDate());
        dto.setEndDate(assign.getJob().getEndDate());
        dto.setWorkingHours(assign.getJob().getWorkingHours());

        dto.setFarmerId(assign.getJob().getFarmer().getId());
        dto.setFarmerName(assign.getJob().getFarmer().getFullName());
        dto.setFarmerPhone(assign.getJob().getFarmer().getPhoneNumber()); // Mutual contact authorized

        dto.setLabourerId(assign.getLabourer().getId());
        dto.setLabourerName(assign.getLabourer().getFullName());
        dto.setLabourerPhone(assign.getLabourer().getPhoneNumber()); // Mutual contact authorized

        dto.setStatus(assign.getStatus());
        dto.setAgreedWage(assign.getAgreedWage());
        dto.setWageBasis(assign.getWageBasis());
        dto.setLabourerConfirmedCompletion(assign.isLabourerConfirmedCompletion());
        dto.setFarmerConfirmedCompletion(assign.isFarmerConfirmedCompletion());
        dto.setCancellationReason(assign.getCancellationReason());
        dto.setCreatedAt(assign.getCreatedAt());
        dto.setCompletedAt(assign.getCompletedAt());
        return dto;
    }
}
