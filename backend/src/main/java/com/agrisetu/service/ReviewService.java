package com.agrisetu.service;

import com.agrisetu.dto.ReviewCreateRequest;
import com.agrisetu.dto.ReviewDto;
import com.agrisetu.entity.*;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.exception.UnauthorizedException;
import com.agrisetu.repository.AssignmentRepository;
import com.agrisetu.repository.ReviewRepository;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AssignmentRepository assignmentRepository;
    private final NotificationService notificationService;
    private final SecurityUtils securityUtils;

    public ReviewService(ReviewRepository reviewRepository,
                         AssignmentRepository assignmentRepository,
                         NotificationService notificationService,
                         SecurityUtils securityUtils) {
        this.reviewRepository = reviewRepository;
        this.assignmentRepository = assignmentRepository;
        this.notificationService = notificationService;
        this.securityUtils = securityUtils;
    }

    @Transactional
    public ReviewDto createReview(ReviewCreateRequest request) {
        User reviewer = securityUtils.getCurrentUser();
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + request.getAssignmentId()));

        if (assignment.getStatus() != AssignmentStatus.COMPLETED) {
            throw new BadRequestException("Reviews can only be submitted after work is marked completed");
        }

        boolean isFarmer = assignment.getJob().getFarmer().getId().equals(reviewer.getId());
        boolean isLabourer = assignment.getLabourer().getId().equals(reviewer.getId());

        if (!isFarmer && !isLabourer) {
            throw new UnauthorizedException("Only participants in this assignment can submit a review");
        }

        if (reviewRepository.existsByAssignmentIdAndReviewerId(assignment.getId(), reviewer.getId())) {
            throw new BadRequestException("You have already submitted a review for this assignment");
        }

        User targetUser = isFarmer ? assignment.getLabourer() : assignment.getJob().getFarmer();

        Review review = new Review(
                assignment,
                reviewer,
                targetUser,
                request.getRating(),
                request.getComment() != null ? request.getComment().trim() : null
        );
        review = reviewRepository.save(review);

        // Notify target user of review
        notificationService.createNotification(
                targetUser,
                "REVIEW_RECEIVED",
                "New Rating & Feedback Received",
                reviewer.getFullName() + " gave you a " + review.getRating() + "-star rating.",
                "REVIEW",
                review.getId()
        );

        return mapToDto(review);
    }

    public List<ReviewDto> getReviewsForUser(Long userId) {
        return reviewRepository.findByTargetUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReviewDto mapToDto(Review r) {
        return new ReviewDto(
                r.getId(),
                r.getAssignment().getId(),
                r.getReviewer().getId(),
                r.getReviewer().getFullName(),
                r.getTargetUser().getId(),
                r.getTargetUser().getFullName(),
                r.getRating(),
                r.getComment(),
                r.getCreatedAt()
        );
    }
}
