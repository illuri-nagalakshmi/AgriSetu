package com.agrisetu.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Jobs", indexes = {
    @Index(name = "IX_Jobs_Status_District", columnList = "status, district"),
    @Index(name = "IX_Jobs_Category", columnList = "category"),
    @Index(name = "IX_Jobs_FarmerId", columnList = "farmer_id")
})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "farm_location", nullable = false, length = 200)
    private String farmLocation;

    @Column(nullable = false, length = 100)
    private String village;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "working_hours", nullable = false, length = 50)
    private String workingHours;

    @Column(name = "wage_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal wageAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "wage_basis", nullable = false, length = 20)
    private WageBasis wageBasis;

    @Column(name = "required_workers", nullable = false)
    private int requiredWorkers;

    @Column(name = "confirmed_workers", nullable = false)
    private int confirmedWorkers = 0;

    @Column(name = "remaining_vacancies", nullable = false)
    private int remainingVacancies;

    @Column(name = "meals_provided", nullable = false)
    private boolean mealsProvided = false;

    @Column(name = "accommodation_provided", nullable = false)
    private boolean accommodationProvided = false;

    @Column(name = "transportation_provided", nullable = false)
    private boolean transportationProvided = false;

    @Column(name = "safety_instructions", length = 500)
    private String safetyInstructions;

    @Column(name = "application_deadline", nullable = false)
    private LocalDate applicationDeadline;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobStatus status = JobStatus.PUBLISHED;

    @Version
    @Column(nullable = false)
    private Long version = 0L;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Job() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.remainingVacancies == 0 && this.requiredWorkers > 0 && this.confirmedWorkers == 0) {
            this.remainingVacancies = this.requiredWorkers;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getFarmer() { return farmer; }
    public void setFarmer(User farmer) { this.farmer = farmer; }

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

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
