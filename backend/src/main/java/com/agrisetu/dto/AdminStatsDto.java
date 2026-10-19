package com.agrisetu.dto;

public class AdminStatsDto {
    private long totalUsers;
    private long totalFarmers;
    private long totalLabourers;
    private long totalJobs;
    private long activeJobs;
    private long totalApplications;
    private long totalAssignments;
    private long pendingReports;

    public AdminStatsDto() {}

    public AdminStatsDto(long totalUsers, long totalFarmers, long totalLabourers,
                         long totalJobs, long activeJobs, long totalApplications,
                         long totalAssignments, long pendingReports) {
        this.totalUsers = totalUsers;
        this.totalFarmers = totalFarmers;
        this.totalLabourers = totalLabourers;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
        this.totalAssignments = totalAssignments;
        this.pendingReports = pendingReports;
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalFarmers() { return totalFarmers; }
    public void setTotalFarmers(long totalFarmers) { this.totalFarmers = totalFarmers; }

    public long getTotalLabourers() { return totalLabourers; }
    public void setTotalLabourers(long totalLabourers) { this.totalLabourers = totalLabourers; }

    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }

    public long getActiveJobs() { return activeJobs; }
    public void setActiveJobs(long activeJobs) { this.activeJobs = activeJobs; }

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }

    public long getTotalAssignments() { return totalAssignments; }
    public void setTotalAssignments(long totalAssignments) { this.totalAssignments = totalAssignments; }

    public long getPendingReports() { return pendingReports; }
    public void setPendingReports(long pendingReports) { this.pendingReports = pendingReports; }
}
