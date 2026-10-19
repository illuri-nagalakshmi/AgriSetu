package com.agrisetu.dto;

import com.agrisetu.entity.AvailabilityStatus;
import java.math.BigDecimal;

public class LabourerProfileDto {
    private String skills;
    private int experienceYears;
    private String preferredCategories;
    private BigDecimal expectedDailyWage;
    private AvailabilityStatus availabilityStatus;

    public LabourerProfileDto() {}

    public LabourerProfileDto(String skills, int experienceYears, String preferredCategories,
                              BigDecimal expectedDailyWage, AvailabilityStatus availabilityStatus) {
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.preferredCategories = preferredCategories;
        this.expectedDailyWage = expectedDailyWage;
        this.availabilityStatus = availabilityStatus;
    }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public String getPreferredCategories() { return preferredCategories; }
    public void setPreferredCategories(String preferredCategories) { this.preferredCategories = preferredCategories; }

    public BigDecimal getExpectedDailyWage() { return expectedDailyWage; }
    public void setExpectedDailyWage(BigDecimal expectedDailyWage) { this.expectedDailyWage = expectedDailyWage; }

    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}
