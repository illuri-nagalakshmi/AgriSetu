package com.agrisetu.dto;

public class DashboardStatsDto {
    // Farmer specific statistics
    private long totalJobs;
    private long activeJobs;
    private long applicationsReceived;
    private long confirmedWorkers;
    private long remainingVacancies;

    // Labourer specific statistics
    private long totalApplications;
    private long pendingApplications;
    private long acceptedApplications;
    private long activeAssignments;
    private long completedAssignments;

    // Common
    private long unreadNotifications;
    private double averageRating;
    private long totalReviews;

    public DashboardStatsDto() {}

    // Getters and Setters
    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }

    public long getActiveJobs() { return activeJobs; }
    public void setActiveJobs(long activeJobs) { this.activeJobs = activeJobs; }

    public long getApplicationsReceived() { return applicationsReceived; }
    public void setApplicationsReceived(long applicationsReceived) { this.applicationsReceived = applicationsReceived; }

    public long getConfirmedWorkers() { return confirmedWorkers; }
    public void setConfirmedWorkers(long confirmedWorkers) { this.confirmedWorkers = confirmedWorkers; }

    public long getRemainingVacancies() { return remainingVacancies; }
    public void setRemainingVacancies(long remainingVacancies) { this.remainingVacancies = remainingVacancies; }

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }

    public long getPendingApplications() { return pendingApplications; }
    public void setPendingApplications(long pendingApplications) { this.pendingApplications = pendingApplications; }

    public long getAcceptedApplications() { return acceptedApplications; }
    public void setAcceptedApplications(long acceptedApplications) { this.acceptedApplications = acceptedApplications; }

    public long getActiveAssignments() { return activeAssignments; }
    public void setActiveAssignments(long activeAssignments) { this.activeAssignments = activeAssignments; }

    public long getCompletedAssignments() { return completedAssignments; }
    public void setCompletedAssignments(long completedAssignments) { this.completedAssignments = completedAssignments; }

    public long getUnreadNotifications() { return unreadNotifications; }
    public void setUnreadNotifications(long unreadNotifications) { this.unreadNotifications = unreadNotifications; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public long getTotalReviews() { return totalReviews; }
    public void setTotalReviews(long totalReviews) { this.totalReviews = totalReviews; }
}
