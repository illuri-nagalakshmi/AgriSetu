package com.agrisetu.dto;

import com.agrisetu.entity.AssignmentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssignmentDto {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String farmLocation;
    private LocalDate startDate;
    private LocalDate endDate;
    private String workingHours;

    private Long farmerId;
    private String farmerName;
    private String farmerPhone;

    private Long labourerId;
    private String labourerName;
    private String labourerPhone;

    private AssignmentStatus status;
    private BigDecimal agreedWage;
    private String wageBasis;
    private boolean labourerConfirmedCompletion;
    private boolean farmerConfirmedCompletion;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private boolean reviewedByMe;

    public AssignmentDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getFarmLocation() { return farmLocation; }
    public void setFarmLocation(String farmLocation) { this.farmLocation = farmLocation; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }

    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }

    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }

    public String getFarmerPhone() { return farmerPhone; }
    public void setFarmerPhone(String farmerPhone) { this.farmerPhone = farmerPhone; }

    public Long getLabourerId() { return labourerId; }
    public void setLabourerId(Long labourerId) { this.labourerId = labourerId; }

    public String getLabourerName() { return labourerName; }
    public void setLabourerName(String labourerName) { this.labourerName = labourerName; }

    public String getLabourerPhone() { return labourerPhone; }
    public void setLabourerPhone(String labourerPhone) { this.labourerPhone = labourerPhone; }

    public AssignmentStatus getStatus() { return status; }
    public void setStatus(AssignmentStatus status) { this.status = status; }

    public BigDecimal getAgreedWage() { return agreedWage; }
    public void setAgreedWage(BigDecimal agreedWage) { this.agreedWage = agreedWage; }

    public String getWageBasis() { return wageBasis; }
    public void setWageBasis(String wageBasis) { this.wageBasis = wageBasis; }

    public boolean isLabourerConfirmedCompletion() { return labourerConfirmedCompletion; }
    public void setLabourerConfirmedCompletion(boolean labourerConfirmedCompletion) { this.labourerConfirmedCompletion = labourerConfirmedCompletion; }

    public boolean isFarmerConfirmedCompletion() { return farmerConfirmedCompletion; }
    public void setFarmerConfirmedCompletion(boolean farmerConfirmedCompletion) { this.farmerConfirmedCompletion = farmerConfirmedCompletion; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public boolean isReviewedByMe() { return reviewedByMe; }
    public void setReviewedByMe(boolean reviewedByMe) { this.reviewedByMe = reviewedByMe; }
}
