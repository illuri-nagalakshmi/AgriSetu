package com.agrisetu;

import com.agrisetu.dto.RegisterRequest;
import com.agrisetu.entity.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FarmerDashboardAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private com.agrisetu.repository.JobRepository jobRepository;

    @Autowired
    private com.agrisetu.repository.FarmerProfileRepository farmerProfileRepository;

    @Autowired
    private com.agrisetu.repository.UserRepository userRepository;

    @AfterEach
    void tearDown() {
        jobRepository.deleteAll();
        farmerProfileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test farmer register, session persistence, XSRF cookie emission, farmer dashboard jobs, and posting job with header")
    void testFarmerAuthAndJobPost() throws Exception {
        // 1. Register a new farmer 'G Praboji'
        RegisterRequest registerReq = new RegisterRequest();
        registerReq.setFullName("G Praboji");
        registerReq.setEmail("praboji.farmer@example.com");
        registerReq.setPhoneNumber("+919876543210");
        registerReq.setPassword("Password@123");
        registerReq.setConfirmPassword("Password@123");
        registerReq.setRole(Role.FARMER);
        registerReq.setVillage("Tenali");
        registerReq.setDistrict("Guntur");
        registerReq.setState("Andhra Pradesh");
        registerReq.setFarmName("Praboji Farm");
        registerReq.setTotalLandAcres(new BigDecimal("10.00"));
        registerReq.setPrimaryCrops("Paddy, Chilli");
        registerReq.setPrivacyConsent(true);

        MvcResult regResult = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReq)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.fullName").value("G Praboji"))
                .andExpect(jsonPath("$.data.role").value("FARMER"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) regResult.getRequest().getSession(false);
        assertNotNull(session, "Session must not be null after registration");

        // Verify XSRF-TOKEN cookie is set on the response
        Cookie xsrfCookie = regResult.getResponse().getCookie("XSRF-TOKEN");
        assertNotNull(xsrfCookie, "XSRF-TOKEN cookie must be returned on registration response");
        String csrfToken = xsrfCookie.getValue();
        System.out.println(">>> Acquired XSRF-TOKEN: " + csrfToken);

        // 2. Call GET /api/jobs/farmer/my-jobs with the session
        mockMvc.perform(get("/api/jobs/farmer/my-jobs")
                        .session(session)
                        .cookie(xsrfCookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // 3. Post a new job using the raw XSRF-TOKEN header (exactly as the SPA Fetch client sends it)
        Map<String, Object> jobPayload = new HashMap<>();
        jobPayload.put("title", "Paddy Harvesting & Bundling");
        jobPayload.put("category", "Harvesting");
        jobPayload.put("description", "Paddy harvesting work in Tenali");
        jobPayload.put("farmLocation", "Survey 12, Tenali");
        jobPayload.put("village", "Tenali");
        jobPayload.put("district", "Guntur");
        jobPayload.put("state", "Andhra Pradesh");
        jobPayload.put("startDate", LocalDate.now().plusDays(2).toString());
        jobPayload.put("endDate", LocalDate.now().plusDays(5).toString());
        jobPayload.put("workingHours", "07:00 AM - 02:00 PM");
        jobPayload.put("wageAmount", 600.0);
        jobPayload.put("wageBasis", "DAILY");
        jobPayload.put("requiredWorkers", 5);
        jobPayload.put("mealsProvided", true);
        jobPayload.put("accommodationProvided", false);
        jobPayload.put("transportationProvided", true);
        jobPayload.put("applicationDeadline", LocalDate.now().plusDays(1).toString());

        System.out.println("--- Testing POST /api/jobs WITH header X-XSRF-TOKEN ---");
        mockMvc.perform(post("/api/jobs")
                        .session(session)
                        .cookie(xsrfCookie)
                        .header("X-XSRF-TOKEN", csrfToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobPayload)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Paddy Harvesting & Bundling"));

        // 4. Verify job appears in GET /api/jobs/farmer/my-jobs
        mockMvc.perform(get("/api/jobs/farmer/my-jobs")
                        .session(session)
                        .cookie(xsrfCookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].title").value("Paddy Harvesting & Bundling"));
    }
}
