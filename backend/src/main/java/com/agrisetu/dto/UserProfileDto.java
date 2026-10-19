package com.agrisetu.dto;

import com.agrisetu.entity.Role;
import java.time.LocalDateTime;

public class UserProfileDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String preferredLanguage;
    private String village;
    private String district;
    private String state;
    private boolean privacyConsent;
    private boolean shareContactOnAccept;
    private boolean isActive;
    private LocalDateTime createdAt;

    private FarmerProfileDto farmerProfile;
    private LabourerProfileDto labourerProfile;
    private double averageRating;
    private long totalReviews;

    public UserProfileDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public boolean isPrivacyConsent() { return privacyConsent; }
    public void setPrivacyConsent(boolean privacyConsent) { this.privacyConsent = privacyConsent; }

    public boolean isShareContactOnAccept() { return shareContactOnAccept; }
    public void setShareContactOnAccept(boolean shareContactOnAccept) { this.shareContactOnAccept = shareContactOnAccept; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public FarmerProfileDto getFarmerProfile() { return farmerProfile; }
    public void setFarmerProfile(FarmerProfileDto farmerProfile) { this.farmerProfile = farmerProfile; }

    public LabourerProfileDto getLabourerProfile() { return labourerProfile; }
    public void setLabourerProfile(LabourerProfileDto labourerProfile) { this.labourerProfile = labourerProfile; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public long getTotalReviews() { return totalReviews; }
    public void setTotalReviews(long totalReviews) { this.totalReviews = totalReviews; }
}
