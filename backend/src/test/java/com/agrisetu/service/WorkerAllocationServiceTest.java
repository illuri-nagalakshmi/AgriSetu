package com.agrisetu.service;

import com.agrisetu.dto.ApplicationDecisionRequest;
import com.agrisetu.entity.*;
import com.agrisetu.exception.AllocationConflictException;
import com.agrisetu.repository.ApplicationRepository;
import com.agrisetu.repository.AssignmentRepository;
import com.agrisetu.repository.JobRepository;
import com.agrisetu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class WorkerAllocationServiceTest {

    @Autowired
    private WorkerAllocationService workerAllocationService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private com.agrisetu.repository.NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.agrisetu.repository.FarmerProfileRepository farmerProfileRepository;

    @Autowired
    private com.agrisetu.repository.LabourerProfileRepository labourerProfileRepository;

    @Autowired
    private JobService jobService;

    private User farmer;
    private List<User> labourers;
    private Job job10Workers;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
        assignmentRepository.deleteAll();
        applicationRepository.deleteAll();
        jobRepository.deleteAll();
        farmerProfileRepository.deleteAll();
        labourerProfileRepository.deleteAll();
        userRepository.deleteAll();

        // 1. Create Farmer
        farmer = new User("Ramesh Farmer", "ramesh@test.com", "+919000000001", "hash", Role.FARMER, "en", "Tenali", "Guntur", "AP");
        farmer = userRepository.save(farmer);

        // Authenticate as Farmer in Spring Security Context
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(farmer.getEmail(), null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_FARMER")))
        );

        // 2. Create Job requiring 10 workers
        job10Workers = new Job();
        job10Workers.setFarmer(farmer);
        job10Workers.setTitle("Paddy Harvesting (10 Capacity Test)");
        job10Workers.setCategory("Harvesting");
        job10Workers.setDescription("Test job requiring 10 workers");
        job10Workers.setFarmLocation("Farm Plot A");
        job10Workers.setVillage("Tenali");
        job10Workers.setDistrict("Guntur");
        job10Workers.setState("Andhra Pradesh");
        job10Workers.setStartDate(LocalDate.now().plusDays(2));
        job10Workers.setEndDate(LocalDate.now().plusDays(7));
        job10Workers.setWorkingHours("07:00 AM - 02:00 PM");
        job10Workers.setWageAmount(new BigDecimal("600.00"));
        job10Workers.setWageBasis(WageBasis.DAILY);
        job10Workers.setRequiredWorkers(10);
        job10Workers.setConfirmedWorkers(0);
        job10Workers.setRemainingVacancies(10);
        job10Workers.setApplicationDeadline(LocalDate.now().plusDays(1));
        job10Workers.setStatus(JobStatus.PUBLISHED);
        job10Workers = jobRepository.save(job10Workers);

        // 3. Create 15 Labourers
        labourers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            User lab = new User("Labourer " + i, "labourer" + i + "@test.com", "+9190000000" + (10 + i), "hash", Role.LABOURER, "en", "Village", "Guntur", "AP");
            labourers.add(userRepository.save(lab));
        }
    }

    @Test
    @DisplayName("Verify full 10-worker scenario: 15 applicants, accept 6, accept 4, reject excess, cancel releases vacancy")
    void testTenWorkerAllocationScenario() {
        // Step 1: Create 15 applications
        List<Application> apps = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Application app = new Application(job10Workers, labourers.get(i), "I am available to work");
            apps.add(applicationRepository.save(app));
        }
        assertEquals(15, applicationRepository.count());

        // Step 2: Accept 6 applications
        for (int i = 0; i < 6; i++) {
            ApplicationDecisionRequest req = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED, "Accepted spot " + (i + 1));
            workerAllocationService.decideApplication(apps.get(i).getId(), req);
        }

        // Step 3: Verify 6 confirmed workers and 4 vacancies
        Job updatedJob = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(6, updatedJob.getConfirmedWorkers(), "Confirmed workers should be exactly 6");
        assertEquals(4, updatedJob.getRemainingVacancies(), "Remaining vacancies should be exactly 4 (10 - 6)");
        assertEquals(6, assignmentRepository.countByJobIdAndStatus(job10Workers.getId(), AssignmentStatus.CONFIRMED));

        // Step 4: Accept 4 more applications
        for (int i = 6; i < 10; i++) {
            ApplicationDecisionRequest req = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED, "Accepted spot " + (i + 1));
            workerAllocationService.decideApplication(apps.get(i).getId(), req);
        }

        // Step 5: Verify 10 confirmed workers and zero vacancies
        updatedJob = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(10, updatedJob.getConfirmedWorkers(), "Confirmed workers should be exactly 10 (full capacity)");
        assertEquals(0, updatedJob.getRemainingVacancies(), "Remaining vacancies should be exactly 0");

        // Step 6: Verify 11th acceptance attempt is strictly rejected
        ApplicationDecisionRequest excessReq = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED, "Attempting excess worker");
        assertThrows(AllocationConflictException.class, () -> {
            workerAllocationService.decideApplication(apps.get(10).getId(), excessReq);
        }, "Accepting an 11th worker for a 10-worker job must throw AllocationConflictException");

        // Verify capacity has not been breached
        updatedJob = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(10, updatedJob.getConfirmedWorkers());
        assertEquals(0, updatedJob.getRemainingVacancies());

        // Step 7: Verify valid cancellation releases a vacancy correctly
        Assignment firstAssignment = assignmentRepository.findByJobIdOrderByCreatedAtDesc(job10Workers.getId()).get(0);
        workerAllocationService.cancelAssignment(firstAssignment.getId(), "Worker unable to attend due to illness");

        updatedJob = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(9, updatedJob.getConfirmedWorkers(), "Confirmed workers should decrease to 9 after cancellation");
        assertEquals(1, updatedJob.getRemainingVacancies(), "Remaining vacancies should increase to 1 after cancellation");

        // Step 8: Now the 11th application can be accepted for the released vacancy
        workerAllocationService.decideApplication(apps.get(10).getId(), excessReq);
        updatedJob = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(10, updatedJob.getConfirmedWorkers(), "Confirmed workers back to 10");
        assertEquals(0, updatedJob.getRemainingVacancies(), "Remaining vacancies back to 0");
    }

    @Test
    @DisplayName("Concurrent worker acceptance: Capacity is never exceeded under simultaneous requests")
    void testConcurrentWorkerAcceptanceCapacityNeverExceeded() throws InterruptedException {
        // Create 15 applications
        List<Application> apps = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Application app = new Application(job10Workers, labourers.get(i), "Available");
            apps.add(applicationRepository.save(app));
        }

        // Pre-fill 8 spots
        for (int i = 0; i < 8; i++) {
            ApplicationDecisionRequest req = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED, "Pre-accepted");
            workerAllocationService.decideApplication(apps.get(i).getId(), req);
        }

        Job job = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(8, job.getConfirmedWorkers());
        assertEquals(2, job.getRemainingVacancies(), "Only 2 vacancies left!");

        // Launch 7 threads simultaneously competing for the last 2 spots!
        int numThreads = 7;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 8; i < 15; i++) {
            final Long appId = apps.get(i).getId();
            executor.submit(() -> {
                try {
                    // Set security context for thread
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(farmer.getEmail(), null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_FARMER")))
                    );
                    latch.await();
                    ApplicationDecisionRequest req = new ApplicationDecisionRequest(ApplicationStatus.ACCEPTED, "Concurrent test");
                    workerAllocationService.decideApplication(appId, req);
                    successCount.incrementAndGet();
                } catch (AllocationConflictException e) {
                    failureCount.incrementAndGet();
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                }
            });
        }

        // Release all threads simultaneously
        latch.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));

        // Exactly 2 of the 7 competing threads must have succeeded!
        Job finalJob = jobRepository.findById(job10Workers.getId()).orElseThrow();
        assertEquals(10, finalJob.getConfirmedWorkers(), "Confirmed workers must NOT exceed 10");
        assertEquals(0, finalJob.getRemainingVacancies(), "Remaining vacancies must be 0");
        assertEquals(2, successCount.get(), "Exactly 2 remaining spots should be accepted");
        assertEquals(5, failureCount.get(), "The other 5 requests should be rejected");
    }

    @Test
    @DisplayName("Verify searchJobs and getFeaturedJobs load successfully without LazyInitializationException")
    void testSearchJobsAndFeaturedJobs() {
        var page = jobService.searchJobs(null, null, null, null, null, null, null, "newest", 0, 10);
        assertNotNull(page);
        assertFalse(page.isEmpty());
        assertEquals("Paddy Harvesting (10 Capacity Test)", page.getContent().get(0).getTitle());
        assertEquals(farmer.getFullName(), page.getContent().get(0).getFarmerName());

        var featured = jobService.getFeaturedJobs();
        assertNotNull(featured);
        assertFalse(featured.isEmpty());
        assertEquals(farmer.getFullName(), featured.get(0).getFarmerName());
    }
}
