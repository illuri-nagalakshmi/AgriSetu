package com.agrisetu.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Assignments",
    uniqueConstraints = @UniqueConstraint(name = "UQ_Assignment_Job_Labourer", columnNames = {"job_id", "labourer_id"}),
    indexes = {
        @Index(name = "IX_Assignments_Job_Status", columnList = "job_id, status"),
        @Index(name = "IX_Assignments_Labourer", columnList = "labourer_id")
    }
)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "labourer_id", nullable = false)
    private User labourer;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AssignmentStatus status = AssignmentStatus.CONFIRMED;

    @Column(name = "agreed_wage", nullable = false, precision = 10, scale = 2)
    private BigDecimal agreedWage;

    @Column(name = "wage_basis", nullable = false, length = 20)
    private String wageBasis;

    @Column(name = "labourer_confirmed_completion", nullable = false)
    private boolean labourerConfirmedCompletion = false;

    @Column(name = "farmer_confirmed_completion", nullable = false)
    private boolean farmerConfirmedCompletion = false;

    @Column(name = "cancellation_reason", length = 300)
    private String cancellationReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public Assignment() {}

    public Assignment(Job job, User labourer, Application application, BigDecimal agreedWage, String wageBasis) {
        this.job = job;
        this.labourer = labourer;
        this.application = application;
        this.agreedWage = agreedWage;
        this.wageBasis = wageBasis;
        this.status = AssignmentStatus.CONFIRMED;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public User getLabourer() { return labourer; }
    public void setLabourer(User labourer) { this.labourer = labourer; }

    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }

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
}
