package com.agrisetu.dto;

import java.time.LocalDateTime;

public class ReviewDto {
    private Long id;
    private Long assignmentId;
    private Long reviewerId;
    private String reviewerName;
    private Long targetUserId;
    private String targetUserName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewDto() {}

    public ReviewDto(Long id, Long assignmentId, Long reviewerId, String reviewerName,
                     Long targetUserId, String targetUserName, int rating, String comment,
                     LocalDateTime createdAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.targetUserId = targetUserId;
        this.targetUserName = targetUserName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }

    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public Long getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Long targetUserId) { this.targetUserId = targetUserId; }

    public String getTargetUserName() { return targetUserName; }
    public void setTargetUserName(String targetUserName) { this.targetUserName = targetUserName; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
