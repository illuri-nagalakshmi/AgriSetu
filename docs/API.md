# AgriSetu REST API Documentation

This document describes the REST APIs exposed by the AgriSetu Spring Boot backend.

Base URL: `http://localhost:8080/api`

---

## 1. Authentication & Session Module (`/api/auth`)

### 1.1 Register Account
- **Endpoint**: `POST /api/auth/register`
- **Access**: Public
- **Request Body**:
  ```json
  {
    "fullName": "Ramesh Kumar",
    "email": "ramesh.farmer@example.com",
    "phoneNumber": "+919848011223",
    "password": "Password@123",
    "confirmPassword": "Password@123",
    "role": "FARMER", // or "LABOURER" (ADMIN self-registration is forbidden)
    "preferredLanguage": "te", // "en", "te", "hi"
    "village": "Tenali",
    "district": "Guntur",
    "state": "Andhra Pradesh",
    "privacyConsent": true,
    // Optional Farmer Fields
    "farmName": "Sri Venkateswara Farm",
    "totalLandAcres": 12.5,
    "primaryCrops": "Paddy, Chilli",
    // Optional Labourer Fields
    "skills": "Harvesting, Thresher",
    "experienceYears": 7,
    "expectedDailyWage": 600.00
  }
  ```
- **Response**: `200 OK` (Auto-logs in and sets session cookie)

### 1.2 Login
- **Endpoint**: `POST /api/auth/login`
- **Access**: Public
- **Request Body**:
  ```json
  {
    "email": "ramesh.farmer@example.com",
    "password": "Password@123"
  }
  ```
- **Response**: `200 OK` (Returns user details and sets `JSESSIONID` session cookie)

### 1.3 Logout
- **Endpoint**: `POST /api/auth/logout`
- **Access**: Public / Authenticated
- **Response**: `200 OK` (Invalidates session)

### 1.4 Get Current Active Session
- **Endpoint**: `GET /api/auth/current`
- **Access**: Public
- **Response**: `200 OK` with user details if session active, or `{ "success": false }` if guest.

---

## 2. User & Profile Module (`/api/users`)

### 2.1 Get Current Profile
- **Endpoint**: `GET /api/users/profile`
- **Access**: Authenticated

### 2.2 Update Profile
- **Endpoint**: `PUT /api/users/profile`
- **Access**: Authenticated
- **Request Body**: `UpdateProfileRequest`

### 2.3 Update Preferred Language
- **Endpoint**: `PUT /api/users/language`
- **Access**: Authenticated
- **Request Body**:
  ```json
  {
    "language": "te" // "en", "te", or "hi"
  }
  ```

### 2.4 Get Dashboard Metrics
- **Endpoint**: `GET /api/users/dashboard-stats`
- **Access**: Authenticated

---

## 3. Jobs Marketplace Module (`/api/jobs`)

### 3.1 Post New Job
- **Endpoint**: `POST /api/jobs`
- **Access**: `ROLE_FARMER`, `ROLE_ADMIN`
- **Request Body**:
  ```json
  {
    "title": "Paddy Crop Harvesting",
    "category": "Harvesting",
    "description": "Sickle harvesting across 8 acres. Drinking water provided.",
    "farmLocation": "Survey 45/2",
    "village": "Tenali",
    "district": "Guntur",
    "state": "Andhra Pradesh",
    "startDate": "2026-10-25",
    "endDate": "2026-10-30",
    "workingHours": "07:00 AM - 02:00 PM",
    "wageAmount": 600.00,
    "wageBasis": "DAILY",
    "requiredWorkers": 10,
    "mealsProvided": true,
    "accommodationProvided": false,
    "transportationProvided": true,
    "safetyInstructions": "Protective boots recommended",
    "applicationDeadline": "2026-10-24"
  }
  ```

### 3.2 Search / Filter Jobs
- **Endpoint**: `GET /api/jobs`
- **Access**: Public
- **Parameters**: `category`, `district`, `state`, `minWage`, `maxWage`, `mealsProvided`, `sortBy`, `page`, `size`

### 3.3 Featured Openings
- **Endpoint**: `GET /api/jobs/featured`
- **Access**: Public

### 3.4 Job Details
- **Endpoint**: `GET /api/jobs/{id}`
- **Access**: Public (Applies contact privacy rules automatically based on authentication)

### 3.5 Update Job Status
- **Endpoint**: `PUT /api/jobs/{id}/status?status=PAUSED`
- **Access**: Owner Farmer or `ROLE_ADMIN`

---

## 4. Worker Allocation & Applications (`/api/applications`)

### 4.1 Apply for Job
- **Endpoint**: `POST /api/applications/apply`
- **Access**: `ROLE_LABOURER`, `ROLE_ADMIN`
- **Request Body**:
  ```json
  {
    "jobId": 1,
    "coverNote": "Available for all 6 days. Can bring bundling sickle."
  }
  ```

### 4.2 Decide Application (Core Allocation)
- **Endpoint**: `POST /api/applications/{id}/decision`
- **Access**: Job Owner Farmer or `ROLE_ADMIN`
- **Request Body**:
  ```json
  {
    "status": "ACCEPTED", // or "REJECTED"
    "decisionReason": "Experienced local harvester"
  }
  ```
- **Concurrency & Logic**:
  - Uses `SELECT ... FOR UPDATE` (Pessimistic write lock) on Job table.
  - Formula: `Remaining Vacancies = Required Workers - Confirmed Workers`.
  - Rejects with `409 Conflict` if capacity reached.
  - Generates `Assignment` upon acceptance and reveals mutual phone numbers.

### 4.3 Withdraw Application
- **Endpoint**: `POST /api/applications/{id}/withdraw`
- **Access**: Applicant Labourer

---

## 5. Work Assignments Module (`/api/assignments`)

### 5.1 My Assignments
- **Endpoint**: `GET /api/assignments/my`
- **Access**: Authenticated

### 5.2 Confirm Work Completion
- **Endpoint**: `POST /api/assignments/{id}/confirm-completion`
- **Access**: Participating Farmer or Labourer

### 5.3 Cancel Assignment (Vacancy Release)
- **Endpoint**: `PUT /api/assignments/{id}/status`
- **Access**: Participating Farmer, Labourer, or Admin
- **Request Body**:
  ```json
  {
    "status": "CANCELLED",
    "cancellationReason": "Illness prevented attendance"
  }
  ```
- **Result**: Cancels assignment, safely releases vacancy (`confirmed_workers - 1`), and notifies both parties.

---

## 6. Reviews & Ratings Module (`/api/reviews`)

### 6.1 Submit Review
- **Endpoint**: `POST /api/reviews`
- **Access**: Eligible participants in a `COMPLETED` assignment
- **Request Body**:
  ```json
  {
    "assignmentId": 1,
    "rating": 5,
    "comment": "Hardworking and punctual worker."
  }
  ```

### 6.2 Get User Reviews
- **Endpoint**: `GET /api/reviews/user/{userId}`
- **Access**: Public

---

## 7. Notifications Module (`/api/notifications`)

- `GET /api/notifications/my` (list notifications)
- `GET /api/notifications/unread-count` (count unread)
- `PUT /api/notifications/{id}/read` (mark single read)
- `PUT /api/notifications/read-all` (mark all read)

---

## 8. Admin Module (`/api/admin`)

- `GET /api/admin/stats` (platform-wide metrics)
- `GET /api/admin/users` (all registered accounts)
- `PUT /api/admin/users/{id}/status?activate=false&reason=...` (suspend/reactivate with audit trail)
- `GET /api/admin/jobs` (moderation list)
- `PUT /api/admin/jobs/{id}/status?status=PAUSED&reason=...` (override status)
- `GET /api/admin/reports` (complaints list)
- `PUT /api/admin/reports/{id}/resolve?status=RESOLVED` (resolve report)
- `GET /api/admin/export/users-csv` (download users CSV)
- `GET /api/admin/export/jobs-csv` (download jobs CSV)
