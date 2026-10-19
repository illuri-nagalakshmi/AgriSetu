package com.agrisetu.dto;

import com.agrisetu.entity.AssignmentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AssignmentStatusUpdateRequest {

    @NotNull(message = "Assignment status is required")
    private AssignmentStatus status;

    @Size(max = 300, message = "Cancellation reason cannot exceed 300 characters")
    private String cancellationReason;

    public AssignmentStatusUpdateRequest() {}

    public AssignmentStatusUpdateRequest(AssignmentStatus status, String cancellationReason) {
        this.status = status;
        this.cancellationReason = cancellationReason;
    }

    public AssignmentStatus getStatus() { return status; }
    public void setStatus(AssignmentStatus status) { this.status = status; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
}
