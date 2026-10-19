-- ============================================================================
-- AgriSetu Database Schema
-- Database: AgriSetuDB
-- Purpose: Community Service Project connecting Farmers and Agricultural Labourers
-- Engine: Microsoft SQL Server
-- ============================================================================

USE [AgriSetuDB];
GO

-- 1. DROP EXISTING TABLES IN REVERSE DEPENDENCY ORDER (SAFE RE-RUNNABILITY)
IF OBJECT_ID(N'dbo.AuditLogs', N'U') IS NOT NULL DROP TABLE dbo.AuditLogs;
IF OBJECT_ID(N'dbo.Reports', N'U') IS NOT NULL DROP TABLE dbo.Reports;
IF OBJECT_ID(N'dbo.Reviews', N'U') IS NOT NULL DROP TABLE dbo.Reviews;
IF OBJECT_ID(N'dbo.Notifications', N'U') IS NOT NULL DROP TABLE dbo.Notifications;
IF OBJECT_ID(N'dbo.SavedJobs', N'U') IS NOT NULL DROP TABLE dbo.SavedJobs;
IF OBJECT_ID(N'dbo.Assignments', N'U') IS NOT NULL DROP TABLE dbo.Assignments;
IF OBJECT_ID(N'dbo.Applications', N'U') IS NOT NULL DROP TABLE dbo.Applications;
IF OBJECT_ID(N'dbo.Jobs', N'U') IS NOT NULL DROP TABLE dbo.Jobs;
IF OBJECT_ID(N'dbo.LabourerProfiles', N'U') IS NOT NULL DROP TABLE dbo.LabourerProfiles;
IF OBJECT_ID(N'dbo.FarmerProfiles', N'U') IS NOT NULL DROP TABLE dbo.FarmerProfiles;
IF OBJECT_ID(N'dbo.Users', N'U') IS NOT NULL DROP TABLE dbo.Users;
GO

-- 2. USERS TABLE
CREATE TABLE dbo.Users (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) NOT NULL UNIQUE,
    phone_number NVARCHAR(20) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    role NVARCHAR(20) NOT NULL CONSTRAINT CHK_User_Role CHECK (role IN (N'FARMER', N'LABOURER', N'ADMIN')),
    preferred_language NVARCHAR(10) NOT NULL DEFAULT N'en' CONSTRAINT CHK_User_Lang CHECK (preferred_language IN (N'en', N'te', N'hi')),
    village NVARCHAR(100) NOT NULL,
    district NVARCHAR(100) NOT NULL,
    state NVARCHAR(100) NOT NULL,
    privacy_consent BIT NOT NULL DEFAULT 1,
    share_contact_on_accept BIT NOT NULL DEFAULT 1,
    is_active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

CREATE INDEX IX_Users_Role ON dbo.Users(role);
CREATE INDEX IX_Users_District_State ON dbo.Users(district, state);
GO

-- 3. FARMER PROFILES TABLE
CREATE TABLE dbo.FarmerProfiles (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE CONSTRAINT FK_Farmer_User REFERENCES dbo.Users(id) ON DELETE CASCADE,
    farm_name NVARCHAR(150) NULL,
    total_land_acres DECIMAL(6,2) NULL,
    primary_crops NVARCHAR(255) NULL,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

-- 4. LABOURER PROFILES TABLE
CREATE TABLE dbo.LabourerProfiles (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE CONSTRAINT FK_Labourer_User REFERENCES dbo.Users(id) ON DELETE CASCADE,
    skills NVARCHAR(300) NULL,
    experience_years INT NOT NULL DEFAULT 0,
    preferred_categories NVARCHAR(300) NULL,
    expected_daily_wage DECIMAL(10,2) NULL,
    availability_status NVARCHAR(30) NOT NULL DEFAULT N'AVAILABLE' 
        CONSTRAINT CHK_Labourer_Avail CHECK (availability_status IN (N'AVAILABLE', N'BUSY', N'UNAVAILABLE')),
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

-- 5. JOBS TABLE
CREATE TABLE dbo.Jobs (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    farmer_id BIGINT NOT NULL CONSTRAINT FK_Job_Farmer REFERENCES dbo.Users(id),
    title NVARCHAR(200) NOT NULL,
    category NVARCHAR(100) NOT NULL CONSTRAINT CHK_Job_Category CHECK (category IN (
        N'Sowing', N'Ploughing', N'Harvesting', N'Weeding', N'Irrigation', 
        N'Planting', N'Fertilizer Application', N'Fruit Picking', N'General Agricultural Labour'
    )),
    description NVARCHAR(MAX) NOT NULL,
    farm_location NVARCHAR(200) NOT NULL,
    village NVARCHAR(100) NOT NULL,
    district NVARCHAR(100) NOT NULL,
    state NVARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    working_hours NVARCHAR(50) NOT NULL,
    wage_amount DECIMAL(10,2) NOT NULL,
    wage_basis NVARCHAR(20) NOT NULL CONSTRAINT CHK_Job_WageBasis CHECK (wage_basis IN (N'DAILY', N'HOURLY')),
    required_workers INT NOT NULL CONSTRAINT CHK_Job_ReqWorkers CHECK (required_workers > 0),
    confirmed_workers INT NOT NULL DEFAULT 0 CONSTRAINT CHK_Job_ConfWorkers CHECK (confirmed_workers >= 0),
    remaining_vacancies INT NOT NULL CONSTRAINT CHK_Job_Vacancies CHECK (remaining_vacancies >= 0),
    meals_provided BIT NOT NULL DEFAULT 0,
    accommodation_provided BIT NOT NULL DEFAULT 0,
    transportation_provided BIT NOT NULL DEFAULT 0,
    safety_instructions NVARCHAR(500) NULL,
    application_deadline DATE NOT NULL,
    image_url NVARCHAR(500) NULL,
    status NVARCHAR(30) NOT NULL DEFAULT N'PUBLISHED' 
        CONSTRAINT CHK_Job_Status CHECK (status IN (N'DRAFT', N'PUBLISHED', N'PAUSED', N'CLOSED', N'CANCELLED', N'COMPLETED')),
    version BIGINT NOT NULL DEFAULT 0, -- For optimistic concurrency locking
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT CHK_Job_Confirmed_NotExceed_Required CHECK (confirmed_workers <= required_workers),
    CONSTRAINT CHK_Job_Date_Order CHECK (end_date >= start_date)
);
GO

CREATE INDEX IX_Jobs_Status_District ON dbo.Jobs(status, district);
CREATE INDEX IX_Jobs_Category ON dbo.Jobs(category);
CREATE INDEX IX_Jobs_FarmerId ON dbo.Jobs(farmer_id);
GO

-- 6. APPLICATIONS TABLE
CREATE TABLE dbo.Applications (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    job_id BIGINT NOT NULL CONSTRAINT FK_Application_Job REFERENCES dbo.Jobs(id),
    labourer_id BIGINT NOT NULL CONSTRAINT FK_Application_Labourer REFERENCES dbo.Users(id),
    status NVARCHAR(30) NOT NULL DEFAULT N'PENDING' 
        CONSTRAINT CHK_App_Status CHECK (status IN (N'PENDING', N'ACCEPTED', N'REJECTED', N'WITHDRAWN')),
    cover_note NVARCHAR(500) NULL,
    applied_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    decided_at DATETIME2(0) NULL,
    decision_reason NVARCHAR(300) NULL,
    CONSTRAINT UQ_Application_Job_Labourer UNIQUE (job_id, labourer_id)
);
GO

CREATE INDEX IX_Applications_JobId_Status ON dbo.Applications(job_id, status);
CREATE INDEX IX_Applications_LabourerId ON dbo.Applications(labourer_id);
GO

-- 7. ASSIGNMENTS TABLE
CREATE TABLE dbo.Assignments (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    job_id BIGINT NOT NULL CONSTRAINT FK_Assignment_Job REFERENCES dbo.Jobs(id),
    labourer_id BIGINT NOT NULL CONSTRAINT FK_Assignment_Labourer REFERENCES dbo.Users(id),
    application_id BIGINT NOT NULL CONSTRAINT FK_Assignment_App REFERENCES dbo.Applications(id),
    status NVARCHAR(30) NOT NULL DEFAULT N'CONFIRMED' 
        CONSTRAINT CHK_Assign_Status CHECK (status IN (N'CONFIRMED', N'IN_PROGRESS', N'COMPLETED', N'CANCELLED')),
    agreed_wage DECIMAL(10,2) NOT NULL,
    wage_basis NVARCHAR(20) NOT NULL,
    labourer_confirmed_completion BIT NOT NULL DEFAULT 0,
    farmer_confirmed_completion BIT NOT NULL DEFAULT 0,
    cancellation_reason NVARCHAR(300) NULL,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    completed_at DATETIME2(0) NULL,
    CONSTRAINT UQ_Assignment_Job_Labourer UNIQUE (job_id, labourer_id)
);
GO

CREATE INDEX IX_Assignments_Job_Status ON dbo.Assignments(job_id, status);
CREATE INDEX IX_Assignments_Labourer ON dbo.Assignments(labourer_id);
GO

-- 8. REVIEWS TABLE
CREATE TABLE dbo.Reviews (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    assignment_id BIGINT NOT NULL CONSTRAINT FK_Review_Assignment REFERENCES dbo.Assignments(id),
    reviewer_id BIGINT NOT NULL CONSTRAINT FK_Review_Reviewer REFERENCES dbo.Users(id),
    target_user_id BIGINT NOT NULL CONSTRAINT FK_Review_Target REFERENCES dbo.Users(id),
    rating INT NOT NULL CONSTRAINT CHK_Review_Rating CHECK (rating >= 1 AND rating <= 5),
    comment NVARCHAR(1000) NULL,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT UQ_Review_Assignment_Reviewer UNIQUE (assignment_id, reviewer_id)
);
GO

-- 9. NOTIFICATIONS TABLE
CREATE TABLE dbo.Notifications (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    recipient_id BIGINT NOT NULL CONSTRAINT FK_Notif_Recipient REFERENCES dbo.Users(id) ON DELETE CASCADE,
    type NVARCHAR(50) NOT NULL,
    title NVARCHAR(150) NOT NULL,
    message NVARCHAR(500) NOT NULL,
    related_entity_type NVARCHAR(50) NULL,
    related_entity_id BIGINT NULL,
    is_read BIT NOT NULL DEFAULT 0,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

CREATE INDEX IX_Notifications_Recipient_Read ON dbo.Notifications(recipient_id, is_read);
GO

-- 10. SAVED JOBS (BOOKMARKS) TABLE
CREATE TABLE dbo.SavedJobs (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    labourer_id BIGINT NOT NULL CONSTRAINT FK_Saved_Labourer REFERENCES dbo.Users(id) ON DELETE CASCADE,
    job_id BIGINT NOT NULL CONSTRAINT FK_Saved_Job REFERENCES dbo.Jobs(id),
    saved_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT UQ_SavedJob UNIQUE (labourer_id, job_id)
);
GO

-- 11. REPORTS AND COMPLAINTS TABLE
CREATE TABLE dbo.Reports (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    reporter_id BIGINT NOT NULL CONSTRAINT FK_Report_Reporter REFERENCES dbo.Users(id),
    reported_user_id BIGINT NULL CONSTRAINT FK_Report_ReportedUser REFERENCES dbo.Users(id),
    job_id BIGINT NULL CONSTRAINT FK_Report_Job REFERENCES dbo.Jobs(id),
    reason NVARCHAR(100) NOT NULL,
    details NVARCHAR(1000) NOT NULL,
    status NVARCHAR(30) NOT NULL DEFAULT N'PENDING' 
        CONSTRAINT CHK_Report_Status CHECK (status IN (N'PENDING', N'RESOLVED', N'DISMISSED')),
    admin_notes NVARCHAR(500) NULL,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    resolved_at DATETIME2(0) NULL
);
GO

-- 12. AUDIT LOGS TABLE
CREATE TABLE dbo.AuditLogs (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    actor_id BIGINT NULL CONSTRAINT FK_Audit_Actor REFERENCES dbo.Users(id),
    action NVARCHAR(100) NOT NULL,
    entity_name NVARCHAR(50) NOT NULL,
    entity_id BIGINT NULL,
    details NVARCHAR(1000) NULL,
    ip_address NVARCHAR(50) NULL,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

PRINT 'AgriSetuDB Schema created successfully!';
GO
