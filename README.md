# 🌾 AgriSetu — Connecting Farmers. Empowering Labourers.

> **A Full-Stack Agricultural Technology Platform & Community Service Project (CSP)**  
> Built with HTML5, CSS3, Vanilla JavaScript, Java 17+ Spring Boot 3.x, Maven, and Microsoft SQL Server.

---

## 📖 1. Project Overview

**AgriSetu (The Agricultural Bridge)** is a community service platform created to connect farmers who need skilled agricultural workers with local labourers seeking fair, transparent daily-wage employment.

### Key Capabilities:
- **Zero Middleman Exploitation:** Direct farmer-to-labourer connection with transparent daily or hourly wages.
- **Strict Capacity & Worker Allocation:** Mathematical guarantee ($\text{Remaining Vacancies} = \text{Required Workers} - \text{Confirmed Workers}$) with database row locks preventing overbooking.
- **Contact Privacy by Default:** Phone numbers are protected and only disclosed mutually once an application is accepted.
- **Inclusive Multilingual Support:** First-class Unicode support for **Telugu (తెలుగు)**, **Hindi (हिन्दी)**, and **English**, with instant switching and profile persistence.
- **Role-Based Experience:** Tailored dashboards for **Farmers**, **Agricultural Labourers**, and **System Administrators**.
- **Mutual Review System:** Post-assignment 1-to-5 star ratings and reviews to build verifiable rural work reputations.

---

## 🛠️ 2. Technology Stack

| Layer | Technologies Used |
| :--- | :--- |
| **Frontend** | HTML5, CSS3 (Nature Green Design System), Vanilla JavaScript (ES6+), Fetch API, CSS Grid & Flexbox *(Zero frameworks like React/Angular)* |
| **Backend** | Java 17+ (JDK 26 compatible), Spring Boot 3.3.4, Spring Web, Spring Data JPA, Hibernate, Spring Security, Bean Validation, Maven |
| **Database** | Microsoft SQL Server (2022/2025 Express), `AgriSetuDB`, Microsoft SQL Server JDBC Driver (`mssql-jdbc`) |
| **Testing** | JUnit 5, Spring Boot Test, Concurrency Stress Tests |
| **Security** | BCrypt password hashing, session authentication, CSRF cookie tokens, role-based authorization (RBAC) |

---

## 📂 3. Project Directory Structure

```
AgriSetu/
├── backend/
│   ├── pom.xml                                 # Maven dependencies & build configuration
│   └── src/
│       ├── main/
│       │   ├── java/com/agrisetu/
│       │   │   ├── AgriSetuApplication.java    # Spring Boot Main Entry Point
│       │   │   ├── config/                     # WebConfig, CORS, DataInitializer
│       │   │   ├── controller/                 # REST Controllers (Auth, Jobs, Applications, etc.)
│       │   │   ├── dto/                        # Request and Response Data Transfer Objects
│       │   │   ├── entity/                     # JPA Entities (User, Job, Application, etc.)
│       │   │   ├── exception/                  # Global Exception Handler & Custom Errors
│       │   │   ├── repository/                 # Spring Data JPA Repositories with Pessimistic Locks
│       │   │   ├── security/                   # SecurityConfig, CustomUserDetailsService, Utils
│       │   │   └── service/                    # Business Logic & WorkerAllocationService
│       │   └── resources/
│       │       └── application.properties      # SQL Server & application settings
│       └── test/
│           ├── java/com/agrisetu/
│           │   ├── AgriSetuApplicationTests.java
│           │   └── service/WorkerAllocationServiceTest.java # 10-Worker & Concurrency Tests
│           └── resources/application-test.properties
├── database/
│   ├── schema.sql                              # SQL Server DDL script for AgriSetuDB
│   └── sample-data.sql                         # Realistic Indian agricultural demonstration dataset
├── docs/
│   ├── API.md                                  # Complete REST API specification
│   └── DATABASE.md                             # Entity relationship diagram & schema details
├── frontend/
│   ├── index.html                              # Landing page with search, featured jobs & mission
│   ├── login.html                              # Login page with 1-click demo credential autofill
│   ├── register.html                           # Dynamic Farmer / Labourer registration
│   ├── jobs.html                               # Live agricultural marketplace with search filters
│   ├── job-details.html                        # Comprehensive job page with contact privacy states
│   ├── about.html                              # Community Service Project (CSP) mission details
│   ├── privacy.html                            # Contact privacy standards documentation
│   ├── farmer-dashboard.html                   # Farmer real-time metrics & job tracking
│   ├── labourer-dashboard.html                 # Labourer applications & recommended work
│   ├── create-job.html                         # Post agricultural job with capacity validation
│   ├── manage-jobs.html                        # Manage jobs and toggle publishing status
│   ├── applications.html                       # Farmer applicant decisions with live capacity counter
│   ├── my-applications.html                    # Labourer application history & status tracking
│   ├── assignments.html                        # Active work, direct call buttons & review submission
│   ├── notifications.html                      # In-app alerts and notifications
│   ├── profile.html                            # Profile management & language preference
│   ├── saved-jobs.html                         # Bookmarked job opportunities
│   ├── admin-dashboard.html                    # Platform analytics & CSV exports
│   ├── admin-users.html                        # Account suspension & reactivation with audit logs
│   ├── admin-jobs.html                         # Platform-wide job listing moderation
│   ├── admin-reports.html                      # Community complaints and dispute resolution
│   ├── css/
│   │   ├── style.css                           # Core Nature Green Design System
│   │   └── dashboard.css                       # Layout, metric cards, tables, and modal dialogs
│   ├── js/
│   │   ├── api.js                              # Fetch API client with CSRF & session handling
│   │   ├── auth.js                             # Session state & role page protection
│   │   ├── i18n.js                             # English, Telugu, and Hindi translations
│   │   ├── jobs.js                             # Job card rendering & application modal
│   │   ├── nav.js                              # Reusable navbar with live notification badge
│   │   └── toast.js                            # Floating toast alert banners
│   └── images/
│       └── logo.svg                            # AgriSetu official vector logo
├── .env.example                                # Template for environment variables
├── .gitignore                                  # Git exclusion rules
└── README.md                                   # Comprehensive project manual
```

---

## ⚡ 4. Beginner-Friendly Windows Setup Guide

### Step 1: Verify Installed Tools
Open PowerShell in the project directory (`C:\Users\DELL\Desktop\AgriSetu`):
```powershell
# Verify Java version (Java 17 or compatible version such as JDK 26)
java -version

# Verify Maven
mvn -v

# Verify SQL Server Express is running
Get-Service -Name "MSSQL$SQLEXPRESS"
```

---

### Step 2: Database Setup (`AgriSetuDB`)
The database `AgriSetuDB` has been created on your local Microsoft SQL Server Express instance (`.\SQLEXPRESS`).

To execute the database schema and demonstration dataset via SQL Server command-line:
```powershell
# Run the schema creation script
sqlcmd -S ".\SQLEXPRESS" -E -C -i database\schema.sql

# Run the sample data script
sqlcmd -S ".\SQLEXPRESS" -E -C -i database\sample-data.sql
```
*(Alternatively, you can open SQL Server Management Studio (SSMS), open `database/schema.sql`, connect to `.\SQLEXPRESS`, and click **Execute**).*

---

### Step 3: Run Automated Concurrency & Worker Allocation Tests
Verify the critical worker allocation algorithm before starting the server:
```powershell
cd backend
mvn test
```
**Expected Output:**
```
[INFO] Running com.agrisetu.service.WorkerAllocationServiceTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: ... s
[INFO] BUILD SUCCESS
```

---

### Step 4: Start the Spring Boot Backend
From the `backend` folder:
```powershell
mvn spring-boot:run
```
**Expected Output:**
```
[INFO] Started AgriSetuApplication in 2.8 seconds (process running for 3.2)
```
The backend is now running at `http://localhost:8080`.

---

### Step 5: Access the Web Application
Simply open your web browser and visit:
👉 **`http://localhost:8080/`**

*(Because Spring Boot is configured with WebMvc resource mapping, the entire frontend is automatically served directly from `http://localhost:8080/index.html` without requiring any separate web server!)*

---

## 🔑 5. Demonstration Accounts

All demo accounts share the password: **`Password@123`** *(Admin password is **`Admin@123`**)*.

On the login page (`login.html`), click the quick auto-fill buttons:

| Role | Email | Password | Details |
| :--- | :--- | :--- | :--- |
| **Farmer** | `ramesh.farmer@example.com` | `Password@123` | Ramesh Kumar, Tenali, Guntur (Paddy Farmer) |
| **Farmer** | `suresh.farmer@example.com` | `Password@123` | Suresh Patel, Armoor, Nizamabad (Turmeric Farmer) |
| **Labourer** | `venkat.labourer@example.com` | `Password@123` | Venkat Rao, Harvesting Expert |
| **Labourer** | `lakshmi.labourer@example.com` | `Password@123` | Lakshmi Devi, Sowing & Picking Specialist |
| **Labourer** | `rajesh.labourer@example.com` | `Password@123` | Rajesh Sharma, Ploughing & Irrigation Specialist |
| **Labourer** | `priya.labourer@example.com` | `Password@123` | Priya Singh, General Agricultural Labourer |
| **Admin** | `admin@agrisetu.org` | `Admin@123` | AgriSetu Head Office Super Administrator |

---

## 🛡️ 6. Core Business Logic: Worker Allocation

AgriSetu implements the strict capacity allocation formula:
$$\text{Remaining Vacancies} = \text{Required Workers} - \text{Confirmed Workers}$$

### The 10-Worker Demonstration Scenario:
1. Farmer Ramesh posts a job for **10 workers**.
2. Up to 15 labourers apply.
3. Farmer accepts 6 workers:
   - `Confirmed Workers = 6`
   - `Remaining Vacancies = 4`
4. Farmer accepts 4 additional workers:
   - `Confirmed Workers = 10`
   - `Remaining Vacancies = 0`
5. Attempting to accept an 11th worker immediately triggers an `AllocationConflictException` (`409 Conflict`), strictly preventing overbooking.
6. When an existing assignment is cancelled, the vacancy is released back:
   - `Confirmed Workers = 9`
   - `Remaining Vacancies = 1`

Automated concurrency stress tests in `WorkerAllocationServiceTest.java` simulate 7 simultaneous threads competing for 2 final spots and guarantee that capacity is never exceeded.

---

## 📚 7. Documentation Links
- [REST API Documentation](docs/API.md)
- [Database Schema & ERD Documentation](docs/DATABASE.md)
- [SQL Schema Script](database/schema.sql)
- [SQL Sample Data Script](database/sample-data.sql)
