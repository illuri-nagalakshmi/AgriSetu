package com.agrisetu.service;

import com.agrisetu.dto.*;
import com.agrisetu.entity.*;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.exception.UnauthorizedException;
import com.agrisetu.repository.*;
import com.agrisetu.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final ApplicationRepository applicationRepository;
    private final SavedJobRepository savedJobRepository;
    private final SecurityUtils securityUtils;
    private final UserService userService;

    public JobService(JobRepository jobRepository,
                      UserRepository userRepository,
                      FarmerProfileRepository farmerProfileRepository,
                      ApplicationRepository applicationRepository,
                      SavedJobRepository savedJobRepository,
                      SecurityUtils securityUtils,
                      UserService userService) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.farmerProfileRepository = farmerProfileRepository;
        this.applicationRepository = applicationRepository;
        this.savedJobRepository = savedJobRepository;
        this.securityUtils = securityUtils;
        this.userService = userService;
    }

    @Transactional
    public JobDetailDto createJob(JobCreateRequest request) {
        User farmer = securityUtils.getCurrentUser();
        if (farmer.getRole() != Role.FARMER && farmer.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only registered farmers can post jobs");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("Job end date cannot be before start date");
        }

        if (request.getApplicationDeadline().isAfter(request.getStartDate())) {
            throw new BadRequestException("Application deadline must be on or before the job start date");
        }

        Job job = new Job();
        job.setFarmer(farmer);
        job.setTitle(request.getTitle().trim());
        job.setCategory(request.getCategory().trim());
        job.setDescription(request.getDescription().trim());
        job.setFarmLocation(request.getFarmLocation().trim());
        job.setVillage(request.getVillage().trim());
        job.setDistrict(request.getDistrict().trim());
        job.setState(request.getState().trim());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setWorkingHours(request.getWorkingHours().trim());
        job.setWageAmount(request.getWageAmount());
        job.setWageBasis(request.getWageBasis());
        job.setRequiredWorkers(request.getRequiredWorkers());
        job.setConfirmedWorkers(0);
        job.setRemainingVacancies(request.getRequiredWorkers());
        job.setMealsProvided(request.isMealsProvided());
        job.setAccommodationProvided(request.isAccommodationProvided());
        job.setTransportationProvided(request.isTransportationProvided());
        job.setSafetyInstructions(request.getSafetyInstructions());
        job.setApplicationDeadline(request.getApplicationDeadline());
        job.setImageUrl(request.getImageUrl());
        job.setStatus(JobStatus.PUBLISHED);

        job = jobRepository.save(job);
        return mapToJobDetailDto(job);
    }

    @Transactional
    public JobDetailDto updateJob(Long jobId, JobUpdateRequest request) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        User currentUser = securityUtils.getCurrentUser();
        if (!job.getFarmer().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to update this job");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("Job end date cannot be before start date");
        }

        if (request.getRequiredWorkers() < job.getConfirmedWorkers()) {
            throw new BadRequestException("Required workers (" + request.getRequiredWorkers() + 
                    ") cannot be less than already confirmed workers (" + job.getConfirmedWorkers() + ")");
        }

        job.setTitle(request.getTitle().trim());
        job.setCategory(request.getCategory().trim());
        job.setDescription(request.getDescription().trim());
        job.setFarmLocation(request.getFarmLocation().trim());
        job.setVillage(request.getVillage().trim());
        job.setDistrict(request.getDistrict().trim());
        job.setState(request.getState().trim());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setWorkingHours(request.getWorkingHours().trim());
        job.setWageAmount(request.getWageAmount());
        job.setWageBasis(request.getWageBasis());
        job.setRequiredWorkers(request.getRequiredWorkers());
        job.setRemainingVacancies(request.getRequiredWorkers() - job.getConfirmedWorkers());
        job.setMealsProvided(request.isMealsProvided());
        job.setAccommodationProvided(request.isAccommodationProvided());
        job.setTransportationProvided(request.isTransportationProvided());
        job.setSafetyInstructions(request.getSafetyInstructions());
        job.setApplicationDeadline(request.getApplicationDeadline());
        if (request.getImageUrl() != null) job.setImageUrl(request.getImageUrl());
        if (request.getStatus() != null) job.setStatus(request.getStatus());

        job = jobRepository.save(job);
        return mapToJobDetailDto(job);
    }

    public JobDetailDto getJobDetails(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        return mapToJobDetailDto(job);
    }

    public Page<JobSummaryDto> searchJobs(String category, String district, String state,
                                          BigDecimal minWage, BigDecimal maxWage,
                                          Boolean mealsProvided, Boolean accommodationProvided,
                                          String sortBy, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if ("wage_asc".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.ASC, "wageAmount");
        } else if ("wage_desc".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "wageAmount");
        } else if ("date".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.ASC, "startDate");
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Job> jobsPage = jobRepository.searchJobs(
                JobStatus.PUBLISHED,
                category, district, state,
                minWage, maxWage,
                mealsProvided, accommodationProvided,
                pageable
        );

        Optional<User> currentUserOpt = securityUtils.getCurrentUserOptional();

        return jobsPage.map(job -> {
            JobSummaryDto dto = mapToJobSummaryDto(job);
            currentUserOpt.ifPresent(u -> {
                if (u.getRole() == Role.LABOURER) {
                    dto.setSaved(savedJobRepository.existsByLabourerIdAndJobId(u.getId(), job.getId()));
                }
            });
            return dto;
        });
    }

    public List<JobSummaryDto> getFeaturedJobs() {
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "createdAt"));
        return jobRepository.findByStatus(JobStatus.PUBLISHED, pageable)
                .map(this::mapToJobSummaryDto)
                .getContent();
    }

    public List<JobSummaryDto> getMyJobs() {
        User farmer = securityUtils.getCurrentUser();
        return jobRepository.findByFarmerIdOrderByCreatedAtDesc(farmer.getId()).stream()
                .map(this::mapToJobSummaryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public JobSummaryDto updateJobStatus(Long jobId, JobStatus newStatus) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        User currentUser = securityUtils.getCurrentUser();
        if (!job.getFarmer().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to update this job status");
        }

        job.setStatus(newStatus);
        job = jobRepository.save(job);
        return mapToJobSummaryDto(job);
    }

    public JobSummaryDto mapToJobSummaryDto(Job job) {
        JobSummaryDto dto = new JobSummaryDto();
        dto.setId(job.getId());
        dto.setFarmerId(job.getFarmer().getId());
        dto.setFarmerName(job.getFarmer().getFullName());
        dto.setTitle(job.getTitle());
        dto.setCategory(job.getCategory());
        dto.setFarmLocation(job.getFarmLocation());
        dto.setVillage(job.getVillage());
        dto.setDistrict(job.getDistrict());
        dto.setState(job.getState());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setWorkingHours(job.getWorkingHours());
        dto.setWageAmount(job.getWageAmount());
        dto.setWageBasis(job.getWageBasis());
        dto.setRequiredWorkers(job.getRequiredWorkers());
        dto.setConfirmedWorkers(job.getConfirmedWorkers());
        dto.setRemainingVacancies(job.getRemainingVacancies());
        dto.setMealsProvided(job.isMealsProvided());
        dto.setAccommodationProvided(job.isAccommodationProvided());
        dto.setTransportationProvided(job.isTransportationProvided());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        dto.setStatus(job.getStatus());
        dto.setCreatedAt(job.getCreatedAt());
        return dto;
    }

    public JobDetailDto mapToJobDetailDto(Job job) {
        JobDetailDto dto = new JobDetailDto();
        dto.setId(job.getId());
        dto.setFarmerId(job.getFarmer().getId());
        dto.setFarmerName(job.getFarmer().getFullName());
        dto.setTitle(job.getTitle());
        dto.setCategory(job.getCategory());
        dto.setDescription(job.getDescription());
        dto.setFarmLocation(job.getFarmLocation());
        dto.setVillage(job.getVillage());
        dto.setDistrict(job.getDistrict());
        dto.setState(job.getState());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setWorkingHours(job.getWorkingHours());
        dto.setWageAmount(job.getWageAmount());
        dto.setWageBasis(job.getWageBasis());
        dto.setRequiredWorkers(job.getRequiredWorkers());
        dto.setConfirmedWorkers(job.getConfirmedWorkers());
        dto.setRemainingVacancies(job.getRemainingVacancies());
        dto.setMealsProvided(job.isMealsProvided());
        dto.setAccommodationProvided(job.isAccommodationProvided());
        dto.setTransportationProvided(job.isTransportationProvided());
        dto.setSafetyInstructions(job.getSafetyInstructions());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        dto.setImageUrl(job.getImageUrl());
        dto.setStatus(job.getStatus());
        dto.setCreatedAt(job.getCreatedAt());

        farmerProfileRepository.findByUserId(job.getFarmer().getId()).ifPresent(fp -> {
            dto.setFarmName(fp.getFarmName());
        });

        // Contact privacy logic
        boolean canReveal = userService.canRevealContact(job.getFarmer().getId());
        dto.setContactVisible(canReveal);
        if (canReveal) {
            dto.setFarmerPhone(job.getFarmer().getPhoneNumber());
        } else {
            dto.setFarmerPhone(userService.maskPhoneNumber(job.getFarmer().getPhoneNumber()));
        }

        // Check current viewer status
        securityUtils.getCurrentUserOptional().ifPresent(u -> {
            if (u.getRole() == Role.LABOURER) {
                dto.setSaved(savedJobRepository.existsByLabourerIdAndJobId(u.getId(), job.getId()));
                applicationRepository.findByJobIdAndLabourerId(job.getId(), u.getId()).ifPresent(app -> {
                    dto.setHasApplied(true);
                    dto.setMyApplicationStatus(app.getStatus().name());
                });
            }
        });

        return dto;
    }
}
