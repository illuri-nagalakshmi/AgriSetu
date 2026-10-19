package com.agrisetu.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SavedJobs",
    uniqueConstraints = @UniqueConstraint(name = "UQ_SavedJob", columnNames = {"labourer_id", "job_id"})
)
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "labourer_id", nullable = false)
    private User labourer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "saved_at", nullable = false, updatable = false)
    private LocalDateTime savedAt;

    public SavedJob() {}

    public SavedJob(User labourer, Job job) {
        this.labourer = labourer;
        this.job = job;
    }

    @PrePersist
    protected void onCreate() {
        this.savedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getLabourer() { return labourer; }
    public void setLabourer(User labourer) { this.labourer = labourer; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }
}
