package com.agrisetu.dto;

import com.agrisetu.entity.WageBasis;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class JobCreateRequest {

    @NotBlank(message = "Job title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "Farm location is required")
    private String farmLocation;

    @NotBlank(message = "Village is required")
    private String village;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Working hours are required (e.g. 07:00 AM - 02:00 PM)")
    private String workingHours;

    @NotNull(message = "Wage amount is required")
    @DecimalMin(value = "50.00", message = "Wage amount must be at least ₹50")
    private BigDecimal wageAmount;

    @NotNull(message = "Wage basis is required (DAILY or HOURLY)")
    private WageBasis wageBasis;

    @Min(value = 1, message = "At least 1 worker must be required")
    private int requiredWorkers;

    private boolean mealsProvided;
    private boolean accommodationProvided;
    private boolean transportationProvided;

    private String safetyInstructions;

    @NotNull(message = "Application deadline is required")
    private LocalDate applicationDeadline;

    private String imageUrl;

    public JobCreateRequest() {}

    // Getters and Setters
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
}
