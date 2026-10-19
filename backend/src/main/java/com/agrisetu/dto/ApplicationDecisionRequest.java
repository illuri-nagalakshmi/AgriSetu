package com.agrisetu.dto;

import com.agrisetu.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApplicationDecisionRequest {

    @NotNull(message = "Decision status is required (ACCEPTED or REJECTED)")
    private ApplicationStatus status;

    @Size(max = 300, message = "Decision reason cannot exceed 300 characters")
    private String decisionReason;

    public ApplicationDecisionRequest() {}

    public ApplicationDecisionRequest(ApplicationStatus status, String decisionReason) {
        this.status = status;
        this.decisionReason = decisionReason;
    }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getDecisionReason() { return decisionReason; }
    public void setDecisionReason(String decisionReason) { this.decisionReason = decisionReason; }
}
