package com.agrisetu.config;

import com.agrisetu.entity.*;
import com.agrisetu.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final LabourerProfileRepository labourerProfileRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AssignmentRepository assignmentRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           FarmerProfileRepository farmerProfileRepository,
                           LabourerProfileRepository labourerProfileRepository,
                           JobRepository jobRepository,
                           ApplicationRepository applicationRepository,
                           AssignmentRepository assignmentRepository,
                           NotificationRepository notificationRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.farmerProfileRepository = farmerProfileRepository;
        this.labourerProfileRepository = labourerProfileRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.assignmentRepository = assignmentRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Ensure default administrator exists
        if (userRepository.findByEmail("admin@agrisetu.org").isEmpty()) {
            log.info("Creating initial AgriSetu Administrator account: admin@agrisetu.org");
            User admin = new User(
                    "AgriSetu Admin",
                    "admin@agrisetu.org",
                    "+919876500000",
                    passwordEncoder.encode("Admin@123"),
                    Role.ADMIN,
                    "en",
                    "Headquarters",
                    "Guntur",
                    "Andhra Pradesh"
            );
            userRepository.save(admin);
        }

        // If no jobs exist, populate demonstration dataset
        if (jobRepository.count() == 0) {
            log.info("Populating realistic agricultural demonstration dataset for AgriSetu...");

            // 1. Create Farmers
            User ramesh = new User(
                    "Ramesh Kumar (రమేష్ కుమార్)",
                    "ramesh.farmer@example.com",
                    "+919848011223",
                    passwordEncoder.encode("Password@123"),
                    Role.FARMER,
                    "te",
                    "Tenali",
                    "Guntur",
                    "Andhra Pradesh"
            );
            ramesh = userRepository.save(ramesh);
            farmerProfileRepository.save(new FarmerProfile(ramesh, "Sri Venkateswara Organic Paddy Fields", new BigDecimal("12.50"), "Paddy, Chilli, Black Gram"));

            User suresh = new User(
                    "Suresh Patel (सुरेश पटेल)",
                    "suresh.farmer@example.com",
                    "+919848033445",
                    passwordEncoder.encode("Password@123"),
                    Role.FARMER,
                    "hi",
                    "Armoor",
                    "Nizamabad",
                    "Telangana"
            );
            suresh = userRepository.save(suresh);
            farmerProfileRepository.save(new FarmerProfile(suresh, "Kisan Krishi Farm", new BigDecimal("18.00"), "Turmeric, Maize, Soyabean"));

            // 2. Create Labourers
            User venkat = new User(
                    "Venkat Rao (వెంకట్ రావు)",
                    "venkat.labourer@example.com",
                    "+919848055667",
                    passwordEncoder.encode("Password@123"),
                    Role.LABOURER,
                    "te",
                    "Angalakuduru",
                    "Guntur",
                    "Andhra Pradesh"
            );
            venkat = userRepository.save(venkat);
            labourerProfileRepository.save(new LabourerProfile(venkat, "Paddy harvesting, Mechanical thresher operation, Weeding", 7, "Harvesting, Weeding, Ploughing", new BigDecimal("600.00")));

            User lakshmi = new User(
                    "Lakshmi Devi (లక్ష్మీ దేవి)",
                    "lakshmi.labourer@example.com",
                    "+919848077889",
                    passwordEncoder.encode("Password@123"),
                    Role.LABOURER,
                    "te",
                    "Kaza",
                    "Guntur",
                    "Andhra Pradesh"
            );
            lakshmi = userRepository.save(lakshmi);
            labourerProfileRepository.save(new LabourerProfile(lakshmi, "Chilli picking, Sowing, Seed treatment, Fruit picking", 5, "Sowing, Fruit Picking, Fertilizer Application", new BigDecimal("550.00")));

            User rajesh = new User(
                    "Rajesh Sharma (राजेश शर्मा)",
                    "rajesh.labourer@example.com",
                    "+919848099001",
                    passwordEncoder.encode("Password@123"),
                    Role.LABOURER,
                    "hi",
                    "Balkonda",
                    "Nizamabad",
                    "Telangana"
            );
            rajesh = userRepository.save(rajesh);
            labourerProfileRepository.save(new LabourerProfile(rajesh, "Turmeric boiling and drying, Tractor ploughing, Irrigation", 8, "Ploughing, Irrigation, Harvesting", new BigDecimal("650.00")));

            User priya = new User(
                    "Priya Singh (प्रिया सिंह)",
                    "priya.labourer@example.com",
                    "+919848022334",
                    passwordEncoder.encode("Password@123"),
                    Role.LABOURER,
                    "hi",
                    "Velpur",
                    "Nizamabad",
                    "Telangana"
            );
            priya = userRepository.save(priya);
            labourerProfileRepository.save(new LabourerProfile(priya, "Maize sowing, Fertilizer scattering, General weeding", 4, "Sowing, Weeding, General Agricultural Labour", new BigDecimal("500.00")));

            // 3. Create Sample Jobs
            // Job 1: 10 Required workers example
            Job job1 = new Job();
            job1.setFarmer(ramesh);
            job1.setTitle("Paddy Crop Harvesting & Bundling (వరి కోత మరియు కట్టలు కట్టుట)");
            job1.setCategory("Harvesting");
            job1.setDescription("Urgent requirement for skilled labourers for traditional sickle harvesting and machine-assisted bundling of Samba Mahsuri paddy across 8 acres. Fresh drinking water and noon meals arranged.");
            job1.setFarmLocation("Survey No. 45/2, Near Canal Road");
            job1.setVillage("Tenali");
            job1.setDistrict("Guntur");
            job1.setState("Andhra Pradesh");
            job1.setStartDate(LocalDate.now().plusDays(3));
            job1.setEndDate(LocalDate.now().plusDays(8));
            job1.setWorkingHours("07:00 AM - 02:00 PM");
            job1.setWageAmount(new BigDecimal("600.00"));
            job1.setWageBasis(WageBasis.DAILY);
            job1.setRequiredWorkers(10);
            job1.setConfirmedWorkers(2); // 2 confirmed in initial demo
            job1.setRemainingVacancies(8); // 8 vacancies remaining
            job1.setMealsProvided(true);
            job1.setAccommodationProvided(false);
            job1.setTransportationProvided(true);
            job1.setSafetyInstructions("Wear protective gumboots and carry personal hand sickles. First aid kit available on site.");
            job1.setApplicationDeadline(LocalDate.now().plusDays(2));
            job1.setStatus(JobStatus.PUBLISHED);
            job1 = jobRepository.save(job1);

            // Job 2: Chilli De-weeding
            Job job2 = new Job();
            job2.setFarmer(ramesh);
            job2.setTitle("Chilli Field De-weeding & Trellis Fixing");
            job2.setCategory("Weeding");
            job2.setDescription("Careful manual weeding required for young Teja chilli plants. Workers must have prior experience in vegetable or spice crops.");
            job2.setFarmLocation("Plot 12, Guntur Rural Bypass");
            job2.setVillage("Tenali");
            job2.setDistrict("Guntur");
            job2.setState("Andhra Pradesh");
            job2.setStartDate(LocalDate.now().plusDays(5));
            job2.setEndDate(LocalDate.now().plusDays(10));
            job2.setWorkingHours("08:00 AM - 03:00 PM");
            job2.setWageAmount(new BigDecimal("550.00"));
            job2.setWageBasis(WageBasis.DAILY);
            job2.setRequiredWorkers(5);
            job2.setConfirmedWorkers(0);
            job2.setRemainingVacancies(5);
            job2.setMealsProvided(true);
            job2.setAccommodationProvided(false);
            job2.setTransportationProvided(false);
            job2.setSafetyInstructions("Sun hats recommended. Refreshments provided twice daily.");
            job2.setApplicationDeadline(LocalDate.now().plusDays(4));
            job2.setStatus(JobStatus.PUBLISHED);
            jobRepository.save(job2);

            // Job 3: Turmeric Harvesting
            Job job3 = new Job();
            job3.setFarmer(suresh);
            job3.setTitle("Turmeric Harvesting & Sorting (हल्दी खुदाई और छंटाई)");
            job3.setCategory("Harvesting");
            job3.setDescription("Harvesting mature turmeric rhizomes, soil shaking, washing, and quality grading for market yard transport.");
            job3.setFarmLocation("Kisan Farm, Near National Highway 44");
            job3.setVillage("Armoor");
            job3.setDistrict("Nizamabad");
            job3.setState("Telangana");
            job3.setStartDate(LocalDate.now().plusDays(4));
            job3.setEndDate(LocalDate.now().plusDays(12));
            job3.setWorkingHours("07:30 AM - 03:30 PM");
            job3.setWageAmount(new BigDecimal("650.00"));
            job3.setWageBasis(WageBasis.DAILY);
            job3.setRequiredWorkers(8);
            job3.setConfirmedWorkers(1);
            job3.setRemainingVacancies(7);
            job3.setMealsProvided(true);
            job3.setAccommodationProvided(true);
            job3.setTransportationProvided(true);
            job3.setSafetyInstructions("Heavy gloves and digging spades supplied by the farm.");
            job3.setApplicationDeadline(LocalDate.now().plusDays(3));
            job3.setStatus(JobStatus.PUBLISHED);
            job3 = jobRepository.save(job3);

            // 4. Create Initial Applications & Assignments
            Application app1 = new Application(job1, venkat, "I have 7 years experience in paddy harvesting in Tenali.");
            app1.setStatus(ApplicationStatus.ACCEPTED);
            app1.setDecidedAt(LocalDateTime.now().minusHours(4));
            app1.setDecisionReason("Experienced local harvester");
            app1 = applicationRepository.save(app1);

            Assignment assign1 = new Assignment(job1, venkat, app1, new BigDecimal("600.00"), "DAILY");
            assignmentRepository.save(assign1);

            Application app2 = new Application(job1, lakshmi, "Available for all 6 days. Can bring bundling sickle.");
            app2.setStatus(ApplicationStatus.ACCEPTED);
            app2.setDecidedAt(LocalDateTime.now().minusHours(2));
            app2.setDecisionReason("Good track record");
            app2 = applicationRepository.save(app2);

            Assignment assign2 = new Assignment(job1, lakshmi, app2, new BigDecimal("600.00"), "DAILY");
            assignmentRepository.save(assign2);

            Application app3 = new Application(job3, rajesh, "Expert in turmeric rhizome harvesting.");
            app3.setStatus(ApplicationStatus.ACCEPTED);
            app3.setDecidedAt(LocalDateTime.now().minusHours(1));
            app3.setDecisionReason("Local expert in Armoor");
            app3 = applicationRepository.save(app3);

            Assignment assign3 = new Assignment(job3, rajesh, app3, new BigDecimal("650.00"), "DAILY");
            assignmentRepository.save(assign3);

            Application app4 = new Application(job3, priya, "Available for sorting and grading turmeric.");
            app4.setStatus(ApplicationStatus.PENDING);
            applicationRepository.save(app4);

            // 5. Initial Notifications
            notificationRepository.save(new Notification(ramesh, "APPLICATION_RECEIVED", "New Application Received", "Lakshmi Devi has applied for Paddy Crop Harvesting.", "JOB", job1.getId()));
            notificationRepository.save(new Notification(venkat, "APPLICATION_ACCEPTED", "Application Accepted!", "Farmer Ramesh Kumar accepted your application for Paddy Crop Harvesting.", "JOB", job1.getId()));
            notificationRepository.save(new Notification(suresh, "APPLICATION_RECEIVED", "New Application Received", "Priya Singh submitted an application for Turmeric Harvesting.", "JOB", job3.getId()));

            log.info("AgriSetu demonstration dataset initialized successfully!");
        }
    }
}
