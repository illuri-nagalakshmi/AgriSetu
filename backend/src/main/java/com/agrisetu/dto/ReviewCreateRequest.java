package com.agrisetu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewCreateRequest {

    @NotNull(message = "Assignment ID is required")
    private Long assignmentId;

    @Min(value = 1, message = "Rating must be between 1 and 5 stars")
    @Max(value = 5, message = "Rating must be between 1 and 5 stars")
    private int rating;

    @Size(max = 1000, message = "Review comment cannot exceed 1000 characters")
    private String comment;

    public ReviewCreateRequest() {}

    public ReviewCreateRequest(Long assignmentId, int rating, String comment) {
        this.assignmentId = assignmentId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
