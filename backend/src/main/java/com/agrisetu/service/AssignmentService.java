package com.agrisetu.service;

import com.agrisetu.dto.AssignmentDto;
import com.agrisetu.dto.AssignmentStatusUpdateRequest;
import com.agrisetu.entity.*;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.exception.UnauthorizedException;
import com.agrisetu.repository.AssignmentRepository;
import com.agrisetu.repository.ReviewRepository;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ReviewRepository reviewRepository;
    private final NotificationService notificationService;
    private final SecurityUtils securityUtils;
    private final WorkerAllocationService workerAllocationService;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             ReviewRepository reviewRepository,
                             NotificationService notificationService,
                             SecurityUtils securityUtils,
                             WorkerAllocationService workerAllocationService) {
        this.assignmentRepository = assignmentRepository;
        this.reviewRepository = reviewRepository;
        this.notificationService = notificationService;
        this.securityUtils = securityUtils;
        this.workerAllocationService = workerAllocationService;
    }

    public List<AssignmentDto> getMyAssignments() {
        User user = securityUtils.getCurrentUser();
        List<Assignment> assignments;
        if (user.getRole() == Role.FARMER) {
            assignments = assignmentRepository.findByFarmerId(user.getId());
        } else if (user.getRole() == Role.LABOURER) {
            assignments = assignmentRepository.findByLabourerIdOrderByCreatedAtDesc(user.getId());
        } else {
            assignments = assignmentRepository.findAll();
        }

        return assignments.stream().map(a -> {
            AssignmentDto dto = workerAllocationService.mapToAssignmentDto(a);
            dto.setReviewedByMe(reviewRepository.existsByAssignmentIdAndReviewerId(a.getId(), user.getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    public AssignmentDto getAssignmentById(Long id) {
        User user = securityUtils.getCurrentUser();
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));

        boolean isFarmer = assignment.getJob().getFarmer().getId().equals(user.getId());
        boolean isLabourer = assignment.getLabourer().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isFarmer && !isLabourer && !isAdmin) {
            throw new UnauthorizedException("You are not authorized to view this assignment");
        }

        AssignmentDto dto = workerAllocationService.mapToAssignmentDto(assignment);
        dto.setReviewedByMe(reviewRepository.existsByAssignmentIdAndReviewerId(assignment.getId(), user.getId()));
        return dto;
    }

    @Transactional
    public AssignmentDto updateAssignmentStatus(Long id, AssignmentStatusUpdateRequest request) {
        if (request.getStatus() == AssignmentStatus.CANCELLED) {
            return workerAllocationService.cancelAssignment(id, request.getCancellationReason());
        }

        User user = securityUtils.getCurrentUser();
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));

        boolean isFarmer = assignment.getJob().getFarmer().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isFarmer && !isAdmin) {
            throw new UnauthorizedException("Only the farmer or admin can update assignment progress");
        }

        assignment.setStatus(request.getStatus());
        if (request.getStatus() == AssignmentStatus.COMPLETED) {
            assignment.setFarmerConfirmedCompletion(true);
            assignment.setLabourerConfirmedCompletion(true);
            assignment.setCompletedAt(LocalDateTime.now());
        }
        assignment = assignmentRepository.save(assignment);
        return workerAllocationService.mapToAssignmentDto(assignment);
    }

    @Transactional
    public AssignmentDto confirmCompletion(Long id) {
        User user = securityUtils.getCurrentUser();
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));

        boolean isFarmer = assignment.getJob().getFarmer().getId().equals(user.getId());
        boolean isLabourer = assignment.getLabourer().getId().equals(user.getId());

        if (!isFarmer && !isLabourer) {
            throw new UnauthorizedException("You are not a participant in this assignment");
        }

        if (assignment.getStatus() == AssignmentStatus.CANCELLED) {
            throw new BadRequestException("Cannot complete a cancelled assignment");
        }

        if (isLabourer) {
            assignment.setLabourerConfirmedCompletion(true);
        }
        if (isFarmer) {
            assignment.setFarmerConfirmedCompletion(true);
        }

        // When farmer confirms (or both confirm), mark completed
        if (assignment.isFarmerConfirmedCompletion()) {
            assignment.setStatus(AssignmentStatus.COMPLETED);
            assignment.setCompletedAt(LocalDateTime.now());

            notificationService.createNotification(
                    assignment.getLabourer(),
                    "ASSIGNMENT_COMPLETED",
                    "Work Completed",
                    "Your agricultural work for '" + assignment.getJob().getTitle() + 
                    "' is marked completed! Please share your rating.",
                    "ASSIGNMENT",
                    assignment.getId()
            );

            notificationService.createNotification(
                    assignment.getJob().getFarmer(),
                    "ASSIGNMENT_COMPLETED",
                    "Work Completed",
                    "Work with " + assignment.getLabourer().getFullName() + " is marked completed! Please rate this worker.",
                    "ASSIGNMENT",
                    assignment.getId()
            );
        } else {
            notificationService.createNotification(
                    assignment.getJob().getFarmer(),
                    "COMPLETION_REQUESTED",
                    "Worker Confirmed Completion",
                    assignment.getLabourer().getFullName() + " marked work completed. Please review and confirm.",
                    "ASSIGNMENT",
                    assignment.getId()
            );
        }

        assignment = assignmentRepository.save(assignment);
        return workerAllocationService.mapToAssignmentDto(assignment);
    }
}
