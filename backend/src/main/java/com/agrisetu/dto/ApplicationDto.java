package com.agrisetu.dto;

import com.agrisetu.entity.ApplicationStatus;
import java.time.LocalDateTime;

public class ApplicationDto {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String jobCategory;
    private Long labourerId;
    private String labourerName;
    private String labourerPhone; // Revealed ONLY when accepted / assignment exists
    private boolean contactVisible;
    private String labourerSkills;
    private int labourerExperienceYears;
    private ApplicationStatus status;
    private String coverNote;
    private LocalDateTime appliedAt;
    private LocalDateTime decidedAt;
    private String decisionReason;

    public ApplicationDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getJobCategory() { return jobCategory; }
    public void setJobCategory(String jobCategory) { this.jobCategory = jobCategory; }

    public Long getLabourerId() { return labourerId; }
    public void setLabourerId(Long labourerId) { this.labourerId = labourerId; }

    public String getLabourerName() { return labourerName; }
    public void setLabourerName(String labourerName) { this.labourerName = labourerName; }

    public String getLabourerPhone() { return labourerPhone; }
    public void setLabourerPhone(String labourerPhone) { this.labourerPhone = labourerPhone; }

    public boolean isContactVisible() { return contactVisible; }
    public void setContactVisible(boolean contactVisible) { this.contactVisible = contactVisible; }

    public String getLabourerSkills() { return labourerSkills; }
    public void setLabourerSkills(String labourerSkills) { this.labourerSkills = labourerSkills; }

    public int getLabourerExperienceYears() { return labourerExperienceYears; }
    public void setLabourerExperienceYears(int labourerExperienceYears) { this.labourerExperienceYears = labourerExperienceYears; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getCoverNote() { return coverNote; }
    public void setCoverNote(String coverNote) { this.coverNote = coverNote; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }

    public LocalDateTime getDecidedAt() { return decidedAt; }
    public void setDecidedAt(LocalDateTime decidedAt) { this.decidedAt = decidedAt; }

    public String getDecisionReason() { return decisionReason; }
    public void setDecisionReason(String decisionReason) { this.decisionReason = decisionReason; }
}
