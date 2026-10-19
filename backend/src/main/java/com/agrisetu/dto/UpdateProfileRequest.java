package com.agrisetu.dto;

import com.agrisetu.entity.AvailabilityStatus;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class UpdateProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Village is required")
    private String village;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    private String preferredLanguage;
    private boolean shareContactOnAccept;

    // Farmer specific fields
    private String farmName;
    private BigDecimal totalLandAcres;
    private String primaryCrops;

    // Labourer specific fields
    private String skills;
    private Integer experienceYears;
    private String preferredCategories;
    private BigDecimal expectedDailyWage;
    private AvailabilityStatus availabilityStatus;

    public UpdateProfileRequest() {}

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public boolean isShareContactOnAccept() { return shareContactOnAccept; }
    public void setShareContactOnAccept(boolean shareContactOnAccept) { this.shareContactOnAccept = shareContactOnAccept; }

    public String getFarmName() { return farmName; }
    public void setFarmName(String farmName) { this.farmName = farmName; }

    public BigDecimal getTotalLandAcres() { return totalLandAcres; }
    public void setTotalLandAcres(BigDecimal totalLandAcres) { this.totalLandAcres = totalLandAcres; }

    public String getPrimaryCrops() { return primaryCrops; }
    public void setPrimaryCrops(String primaryCrops) { this.primaryCrops = primaryCrops; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getPreferredCategories() { return preferredCategories; }
    public void setPreferredCategories(String preferredCategories) { this.preferredCategories = preferredCategories; }

    public BigDecimal getExpectedDailyWage() { return expectedDailyWage; }
    public void setExpectedDailyWage(BigDecimal expectedDailyWage) { this.expectedDailyWage = expectedDailyWage; }

    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}
