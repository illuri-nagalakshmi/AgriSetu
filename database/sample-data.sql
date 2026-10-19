-- ============================================================================
-- AgriSetu Sample Data
-- Database: AgriSetuDB
-- Purpose: Realistic demonstration data for testing the CSP AgriSetu platform
-- Note: All sample passwords are: Password@123
-- BCrypt Hash: $2a$10$w3O1iXk04WfFq69vBvLg/ug16/4Vf7L2tIeR3yC8w9XzU5T7N3XQe
-- ============================================================================

USE [AgriSetuDB];
GO

-- 1. Insert Initial Administrator (Secure setup, never registered through public form)
IF NOT EXISTS (SELECT 1 FROM dbo.Users WHERE email = N'admin@agrisetu.org')
BEGIN
    INSERT INTO dbo.Users (full_name, email, phone_number, password_hash, role, preferred_language, village, district, state, privacy_consent, share_contact_on_accept, is_active)
    VALUES (N'AgriSetu Admin', N'admin@agrisetu.org', N'+919876500000', 
            N'$2a$10$e8460m8F9iY3Yq6Hk83Uje1h7u11u.c0aBvV4Gk0uJ7.Zf.t7X6yq', -- Admin@123
            N'ADMIN', N'en', N'Headquarters', N'Guntur', N'Andhra Pradesh', 1, 1, 1);
END;
GO

-- 2. Insert Farmers
-- Password for all demo accounts: Password@123 ($2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi)
INSERT INTO dbo.Users (full_name, email, phone_number, password_hash, role, preferred_language, village, district, state, privacy_consent, share_contact_on_accept, is_active)
VALUES 
(N'Ramesh Kumar (రమేష్ కుమార్)', N'ramesh.farmer@example.com', N'+919848011223', 
 N'$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', N'FARMER', N'te', N'Tenali', N'Guntur', N'Andhra Pradesh', 1, 1, 1),

(N'Suresh Patel (सुरेश पटेल)', N'suresh.farmer@example.com', N'+919848033445', 
 N'$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', N'FARMER', N'hi', N'Armoor', N'Nizamabad', N'Telangana', 1, 1, 1);
GO

DECLARE @RameshId BIGINT = (SELECT id FROM dbo.Users WHERE email = N'ramesh.farmer@example.com');
DECLARE @SureshId BIGINT = (SELECT id FROM dbo.Users WHERE email = N'suresh.farmer@example.com');

INSERT INTO dbo.FarmerProfiles (user_id, farm_name, total_land_acres, primary_crops)
VALUES 
(@RameshId, N'Sri Venkateswara Organic Paddy Fields', 12.50, N'Paddy, Chilli, Black Gram'),
(@SureshId, N'Kisan Krishi Farm', 18.00, N'Turmeric, Maize, Soyabean');
GO

-- 3. Insert Labourers
INSERT INTO dbo.Users (full_name, email, phone_number, password_hash, role, preferred_language, village, district, state, privacy_consent, share_contact_on_accept, is_active)
VALUES 
(N'Venkat Rao (వెంకట్ రావు)', N'venkat.labourer@example.com', N'+919848055667', 
 N'$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', N'LABOURER', N'te', N'Angalakuduru', N'Guntur', N'Andhra Pradesh', 1, 1, 1),

(N'Lakshmi Devi (లక్ష్మీ దేవి)', N'lakshmi.labourer@example.com', N'+919848077889', 
 N'$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', N'LABOURER', N'te', N'Kaza', N'Guntur', N'Andhra Pradesh', 1, 1, 1),

(N'Rajesh Sharma (राजेश शर्मा)', N'rajesh.labourer@example.com', N'+919848099001', 
 N'$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', N'LABOURER', N'hi', N'Balkonda', N'Nizamabad', N'Telangana', 1, 1, 1),

(N'Priya Singh (प्रिया सिंह)', N'priya.labourer@example.com', N'+919848022334', 
 N'$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', N'LABOURER', N'hi', N'Velpur', N'Nizamabad', N'Telangana', 1, 1, 1);
GO

DECLARE @VenkatId BIGINT = (SELECT id FROM dbo.Users WHERE email = N'venkat.labourer@example.com');
DECLARE @LakshmiId BIGINT = (SELECT id FROM dbo.Users WHERE email = N'lakshmi.labourer@example.com');
DECLARE @RajeshId BIGINT = (SELECT id FROM dbo.Users WHERE email = N'rajesh.labourer@example.com');
DECLARE @PriyaId BIGINT = (SELECT id FROM dbo.Users WHERE email = N'priya.labourer@example.com');

INSERT INTO dbo.LabourerProfiles (user_id, skills, experience_years, preferred_categories, expected_daily_wage, availability_status)
VALUES 
(@VenkatId, N'Paddy harvesting, Mechanical thresher operation, Weeding', 7, N'Harvesting, Weeding, Ploughing', 600.00, N'AVAILABLE'),
(@LakshmiId, N'Chilli picking, Sowing, Seed treatment, Fruit picking', 5, N'Sowing, Fruit Picking, Fertilizer Application', 550.00, N'AVAILABLE'),
(@RajeshId, N'Turmeric boiling and drying, Tractor ploughing, Irrigation', 8, N'Ploughing, Irrigation, Harvesting', 650.00, N'AVAILABLE'),
(@PriyaId, N'Maize sowing, Fertilizer scattering, General weeding', 4, N'Sowing, Weeding, General Agricultural Labour', 500.00, N'AVAILABLE');
GO

-- 4. Insert Demo Jobs
-- Job 1: Paddy Harvesting (Tenali, Guntur) - Demonstrating the 10-worker requirement
DECLARE @RameshId2 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'ramesh.farmer@example.com');
DECLARE @SureshId2 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'suresh.farmer@example.com');

INSERT INTO dbo.Jobs (
    farmer_id, title, category, description, farm_location, village, district, state,
    start_date, end_date, working_hours, wage_amount, wage_basis,
    required_workers, confirmed_workers, remaining_vacancies,
    meals_provided, accommodation_provided, transportation_provided,
    safety_instructions, application_deadline, status
)
VALUES 
(
    @RameshId2,
    N'Paddy Crop Harvesting & Bundling (వరి కోత మరియు కట్టలు కట్టుట)',
    N'Harvesting',
    N'Urgent requirement for skilled labourers for traditional sickle harvesting and machine-assisted bundling of Samba Mahsuri paddy across 8 acres. Drinking water and noon meals arranged.',
    N'Survey No. 45/2, Near Canal Road',
    N'Tenali', N'Guntur', N'Andhra Pradesh',
    DATEADD(day, 3, CAST(GETDATE() AS DATE)),
    DATEADD(day, 8, CAST(GETDATE() AS DATE)),
    N'07:00 AM - 02:00 PM',
    600.00, N'DAILY',
    10, 2, 8, -- 10 required, 2 already confirmed in demo, 8 vacancies remaining
    1, 0, 1,
    N'Wear protective gumboots and carry personal hand sickles. First aid kit on site.',
    DATEADD(day, 2, CAST(GETDATE() AS DATE)),
    N'PUBLISHED'
),
(
    @RameshId2,
    N'Chilli Field De-weeding & Trellis Fixing',
    N'Weeding',
    N'Careful manual weeding required for young Teja chilli plants. Workers must have prior experience in vegetable or spice crops.',
    N'Plot 12, Guntur Rural Bypass',
    N'Tenali', N'Guntur', N'Andhra Pradesh',
    DATEADD(day, 5, CAST(GETDATE() AS DATE)),
    DATEADD(day, 10, CAST(GETDATE() AS DATE)),
    N'08:00 AM - 03:00 PM',
    550.00, N'DAILY',
    5, 0, 5,
    1, 0, 0,
    N'Sun hats recommended. Refreshments provided twice daily.',
    DATEADD(day, 4, CAST(GETDATE() AS DATE)),
    N'PUBLISHED'
),
(
    @SureshId2,
    N'Turmeric Harvesting & Sorting (हल्दी खुदाई और छंटाई)',
    N'Harvesting',
    N'Harvesting mature turmeric rhizomes, soil shaking, washing and quality grading for market yard transport.',
    N'Kisan Farm, Near National Highway 44',
    N'Armoor', N'Nizamabad', N'Telangana',
    DATEADD(day, 4, CAST(GETDATE() AS DATE)),
    DATEADD(day, 12, CAST(GETDATE() AS DATE)),
    N'07:30 AM - 03:30 PM',
    650.00, N'DAILY',
    8, 1, 7,
    1, 1, 1,
    N'Heavy gloves and digging spades supplied by the farm.',
    DATEADD(day, 3, CAST(GETDATE() AS DATE)),
    N'PUBLISHED'
),
(
    @SureshId2,
    N'Drip Irrigation Pipeline Laying & Testing',
    N'Irrigation',
    N'Assistance in laying sub-main PVC pipes and micro-drippers for orchard zone.',
    N'Orchard Block B, Armoor',
    N'Armoor', N'Nizamabad', N'Telangana',
    DATEADD(day, 10, CAST(GETDATE() AS DATE)),
    DATEADD(day, 14, CAST(GETDATE() AS DATE)),
    N'08:00 AM - 04:00 PM',
    700.00, N'DAILY',
    4, 0, 4,
    0, 0, 1,
    N'Safety goggles provided when cutting PVC pipe segments.',
    DATEADD(day, 8, CAST(GETDATE() AS DATE)),
    N'PUBLISHED'
);
GO

-- 5. Insert Applications & Assignments for Demonstration
DECLARE @JobPaddyId BIGINT = (SELECT TOP 1 id FROM dbo.Jobs WHERE title LIKE N'Paddy%');
DECLARE @JobTurmericId BIGINT = (SELECT TOP 1 id FROM dbo.Jobs WHERE title LIKE N'Turmeric%');

DECLARE @VenkatId2 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'venkat.labourer@example.com');
DECLARE @LakshmiId2 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'lakshmi.labourer@example.com');
DECLARE @RajeshId2 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'rajesh.labourer@example.com');
DECLARE @PriyaId2 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'priya.labourer@example.com');

-- Venkat applied & accepted on Paddy Job
INSERT INTO dbo.Applications (job_id, labourer_id, status, cover_note, decided_at, decision_reason)
VALUES (@JobPaddyId, @VenkatId2, N'ACCEPTED', N'I have 7 years experience in paddy harvesting in Tenali.', SYSUTCDATETIME(), N'Experienced local harvester');

DECLARE @VenkatAppId BIGINT = SCOPE_IDENTITY();

INSERT INTO dbo.Assignments (job_id, labourer_id, application_id, status, agreed_wage, wage_basis)
VALUES (@JobPaddyId, @VenkatId2, @VenkatAppId, N'CONFIRMED', 600.00, N'DAILY');

-- Lakshmi applied & accepted on Paddy Job
INSERT INTO dbo.Applications (job_id, labourer_id, status, cover_note, decided_at, decision_reason)
VALUES (@JobPaddyId, @LakshmiId2, N'ACCEPTED', N'Available for all 6 days. Can bring bundling sickle.', SYSUTCDATETIME(), N'Good track record');

DECLARE @LakshmiAppId BIGINT = SCOPE_IDENTITY();

INSERT INTO dbo.Assignments (job_id, labourer_id, application_id, status, agreed_wage, wage_basis)
VALUES (@JobPaddyId, @LakshmiId2, @LakshmiAppId, N'CONFIRMED', 600.00, N'DAILY');

-- Rajesh applied to Turmeric Job & accepted
INSERT INTO dbo.Applications (job_id, labourer_id, status, cover_note, decided_at, decision_reason)
VALUES (@JobTurmericId, @RajeshId2, N'ACCEPTED', N'Expert in turmeric rhizome harvesting.', SYSUTCDATETIME(), N'Local expert in Armoor');

DECLARE @RajeshAppId BIGINT = SCOPE_IDENTITY();

INSERT INTO dbo.Assignments (job_id, labourer_id, application_id, status, agreed_wage, wage_basis)
VALUES (@JobTurmericId, @RajeshId2, @RajeshAppId, N'CONFIRMED', 650.00, N'DAILY');

-- Priya applied to Turmeric Job - Pending decision
INSERT INTO dbo.Applications (job_id, labourer_id, status, cover_note)
VALUES (@JobTurmericId, @PriyaId2, N'PENDING', N'Available for sorting and grading turmeric.');

-- 6. Insert Notifications
DECLARE @RameshId3 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'ramesh.farmer@example.com');
DECLARE @SureshId3 BIGINT = (SELECT id FROM dbo.Users WHERE email = N'suresh.farmer@example.com');

INSERT INTO dbo.Notifications (recipient_id, type, title, message, related_entity_type, related_entity_id, is_read)
VALUES 
(@RameshId3, N'APPLICATION_RECEIVED', N'New Application Received', N'Lakshmi Devi has applied for Paddy Crop Harvesting.', N'JOB', @JobPaddyId, 0),
(@VenkatId2, N'APPLICATION_ACCEPTED', N'Application Accepted!', N'Farmer Ramesh Kumar accepted your application for Paddy Crop Harvesting.', N'JOB', @JobPaddyId, 0),
(@SureshId3, N'APPLICATION_RECEIVED', N'New Application Received', N'Priya Singh submitted an application for Turmeric Harvesting.', N'JOB', @JobTurmericId, 0);

PRINT 'AgriSetu Sample Data inserted successfully!';
GO
