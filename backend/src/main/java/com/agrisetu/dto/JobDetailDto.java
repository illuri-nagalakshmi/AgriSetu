package com.agrisetu.dto;

import com.agrisetu.entity.JobStatus;
import com.agrisetu.entity.WageBasis;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobDetailDto {
    private Long id;
    private Long farmerId;
    private String farmerName;
    private String farmerPhone; // Only visible if authorized!
    private boolean contactVisible;
    private String farmName;
    private String title;
    private String category;
    private String description;
    private String farmLocation;
    private String village;
    private String district;
    private String state;
    private LocalDate startDate;
    private LocalDate endDate;
    private String workingHours;
    private BigDecimal wageAmount;
    private WageBasis wageBasis;
    private int requiredWorkers;
    private int confirmedWorkers;
    private int remainingVacancies;
    private boolean mealsProvided;
    private boolean accommodationProvided;
    private boolean transportationProvided;
    private String safetyInstructions;
    private LocalDate applicationDeadline;
    private String imageUrl;
    private JobStatus status;
    private LocalDateTime createdAt;
    private boolean isSaved;
    private boolean hasApplied;
    private String myApplicationStatus;

    public JobDetailDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }

    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }

    public String getFarmerPhone() { return farmerPhone; }
    public void setFarmerPhone(String farmerPhone) { this.farmerPhone = farmerPhone; }

    public boolean isContactVisible() { return contactVisible; }
    public void setContactVisible(boolean contactVisible) { this.contactVisible = contactVisible; }

    public String getFarmName() { return farmName; }
    public void setFarmName(String farmName) { this.farmName = farmName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFarmLocation() { return farmLocation; }
    public void setFarmLocation(String farmLocation) { this.farmLocation = farmLocation; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }

    public BigDecimal getWageAmount() { return wageAmount; }
    public void setWageAmount(BigDecimal wageAmount) { this.wageAmount = wageAmount; }

    public WageBasis getWageBasis() { return wageBasis; }
    public void setWageBasis(WageBasis wageBasis) { this.wageBasis = wageBasis; }

    public int getRequiredWorkers() { return requiredWorkers; }
    public void setRequiredWorkers(int requiredWorkers) { this.requiredWorkers = requiredWorkers; }

    public int getConfirmedWorkers() { return confirmedWorkers; }
    public void setConfirmedWorkers(int confirmedWorkers) { this.confirmedWorkers = confirmedWorkers; }

    public int getRemainingVacancies() { return remainingVacancies; }
    public void setRemainingVacancies(int remainingVacancies) { this.remainingVacancies = remainingVacancies; }

    public boolean isMealsProvided() { return mealsProvided; }
    public void setMealsProvided(boolean mealsProvided) { this.mealsProvided = mealsProvided; }

    public boolean isAccommodationProvided() { return accommodationProvided; }
    public void setAccommodationProvided(boolean accommodationProvided) { this.accommodationProvided = accommodationProvided; }

    public boolean isTransportationProvided() { return transportationProvided; }
    public void setTransportationProvided(boolean transportationProvided) { this.transportationProvided = transportationProvided; }

    public String getSafetyInstructions() { return safetyInstructions; }
    public void setSafetyInstructions(String safetyInstructions) { this.safetyInstructions = safetyInstructions; }

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isSaved() { return isSaved; }
    public void setSaved(boolean saved) { isSaved = saved; }

    public boolean isHasApplied() { return hasApplied; }
    public void setHasApplied(boolean hasApplied) { this.hasApplied = hasApplied; }

    public String getMyApplicationStatus() { return myApplicationStatus; }
    public void setMyApplicationStatus(String myApplicationStatus) { this.myApplicationStatus = myApplicationStatus; }
}
