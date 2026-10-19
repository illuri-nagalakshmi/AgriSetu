package com.agrisetu.service;

import com.agrisetu.dto.*;
import com.agrisetu.entity.*;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.ResourceNotFoundException;
import com.agrisetu.repository.*;
import com.agrisetu.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final LabourerProfileRepository labourerProfileRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AssignmentRepository assignmentRepository;
    private final NotificationRepository notificationRepository;
    private final ReviewRepository reviewRepository;
    private final SecurityUtils securityUtils;

    public UserService(UserRepository userRepository,
                       FarmerProfileRepository farmerProfileRepository,
                       LabourerProfileRepository labourerProfileRepository,
                       JobRepository jobRepository,
                       ApplicationRepository applicationRepository,
                       AssignmentRepository assignmentRepository,
                       NotificationRepository notificationRepository,
                       ReviewRepository reviewRepository,
                       SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.farmerProfileRepository = farmerProfileRepository;
        this.labourerProfileRepository = labourerProfileRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.assignmentRepository = assignmentRepository;
        this.notificationRepository = notificationRepository;
        this.reviewRepository = reviewRepository;
        this.securityUtils = securityUtils;
    }

    public UserProfileDto getCurrentUserProfile() {
        User user = securityUtils.getCurrentUser();
        return buildUserProfileDto(user);
    }

    public UserProfileDto getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return buildUserProfileDto(user);
    }

    @Transactional
    public UserProfileDto updateProfile(UpdateProfileRequest request) {
        User user = securityUtils.getCurrentUser();

        user.setFullName(request.getFullName().trim());
        user.setVillage(request.getVillage().trim());
        user.setDistrict(request.getDistrict().trim());
        user.setState(request.getState().trim());
        user.setShareContactOnAccept(request.isShareContactOnAccept());

        if (request.getPreferredLanguage() != null && !request.getPreferredLanguage().isBlank()) {
            user.setPreferredLanguage(request.getPreferredLanguage());
        }

        // Validate phone number uniqueness if modified
        if (!user.getPhoneNumber().equals(request.getPhoneNumber().trim())) {
            if (userRepository.existsByPhoneNumber(request.getPhoneNumber().trim())) {
                throw new BadRequestException("Phone number " + request.getPhoneNumber() + " is already in use");
            }
            user.setPhoneNumber(request.getPhoneNumber().trim());
        }

        userRepository.save(user);

        if (user.getRole() == Role.FARMER) {
            FarmerProfile fp = farmerProfileRepository.findByUserId(user.getId())
                    .orElseGet(() -> new FarmerProfile(user, null, null, null));
            fp.setFarmName(request.getFarmName());
            fp.setTotalLandAcres(request.getTotalLandAcres());
            fp.setPrimaryCrops(request.getPrimaryCrops());
            farmerProfileRepository.save(fp);
        } else if (user.getRole() == Role.LABOURER) {
            LabourerProfile lp = labourerProfileRepository.findByUserId(user.getId())
                    .orElseGet(() -> new LabourerProfile(user, null, 0, null, null));
            lp.setSkills(request.getSkills());
            if (request.getExperienceYears() != null) {
                lp.setExperienceYears(request.getExperienceYears());
            }
            lp.setPreferredCategories(request.getPreferredCategories());
            lp.setExpectedDailyWage(request.getExpectedDailyWage());
            if (request.getAvailabilityStatus() != null) {
                lp.setAvailabilityStatus(request.getAvailabilityStatus());
            }
            labourerProfileRepository.save(lp);
        }

        return buildUserProfileDto(user);
    }

    @Transactional
    public void updatePreferredLanguage(String language) {
        User user = securityUtils.getCurrentUser();
        user.setPreferredLanguage(language);
        userRepository.save(user);
    }

    public DashboardStatsDto getDashboardStats() {
        User user = securityUtils.getCurrentUser();
        DashboardStatsDto stats = new DashboardStatsDto();

        stats.setUnreadNotifications(notificationRepository.countByRecipientIdAndIsReadFalse(user.getId()));
        stats.setAverageRating(reviewRepository.getAverageRatingForUser(user.getId()));
        stats.setTotalReviews(reviewRepository.countByTargetUserId(user.getId()));

        if (user.getRole() == Role.FARMER) {
            stats.setTotalJobs(jobRepository.countByFarmerId(user.getId()));
            stats.setActiveJobs(jobRepository.countByFarmerIdAndStatus(user.getId(), JobStatus.PUBLISHED));
            stats.setApplicationsReceived(applicationRepository.countTotalApplicationsForFarmer(user.getId()));
            stats.setConfirmedWorkers(assignmentRepository.countByFarmerIdAndStatus(user.getId(), AssignmentStatus.CONFIRMED));

            // Calculate remaining vacancies across active jobs
            long vacancies = jobRepository.findByFarmerIdOrderByCreatedAtDesc(user.getId()).stream()
                    .filter(j -> j.getStatus() == JobStatus.PUBLISHED)
                    .mapToInt(Job::getRemainingVacancies)
                    .sum();
            stats.setRemainingVacancies(vacancies);
        } else if (user.getRole() == Role.LABOURER) {
            stats.setTotalApplications(applicationRepository.countByLabourerId(user.getId()));
            stats.setPendingApplications(applicationRepository.countByLabourerIdAndStatus(user.getId(), ApplicationStatus.PENDING));
            stats.setAcceptedApplications(applicationRepository.countByLabourerIdAndStatus(user.getId(), ApplicationStatus.ACCEPTED));
            stats.setActiveAssignments(assignmentRepository.countByLabourerIdAndStatus(user.getId(), AssignmentStatus.CONFIRMED));
            stats.setCompletedAssignments(assignmentRepository.countByLabourerIdAndStatus(user.getId(), AssignmentStatus.COMPLETED));
        }

        return stats;
    }

    public boolean canRevealContact(Long targetUserId) {
        return securityUtils.getCurrentUserOptional().map(currentUser -> {
            if (currentUser.getId().equals(targetUserId)) {
                return true;
            }
            if (currentUser.getRole() == Role.ADMIN) {
                return true;
            }
            return assignmentRepository.hasAuthorizedAssignmentBetween(currentUser.getId(), targetUserId);
        }).orElse(false);
    }

    public String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 6) {
            return "**********";
        }
        int len = phone.length();
        return phone.substring(0, 3) + "******" + phone.substring(len - 2);
    }

    private UserProfileDto buildUserProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setPreferredLanguage(user.getPreferredLanguage());
        dto.setVillage(user.getVillage());
        dto.setDistrict(user.getDistrict());
        dto.setState(user.getState());
        dto.setPrivacyConsent(user.isPrivacyConsent());
        dto.setShareContactOnAccept(user.isShareContactOnAccept());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getRole() == Role.FARMER) {
            farmerProfileRepository.findByUserId(user.getId()).ifPresent(fp -> {
                dto.setFarmerProfile(new FarmerProfileDto(fp.getFarmName(), fp.getTotalLandAcres(), fp.getPrimaryCrops()));
            });
        } else if (user.getRole() == Role.LABOURER) {
            labourerProfileRepository.findByUserId(user.getId()).ifPresent(lp -> {
                dto.setLabourerProfile(new LabourerProfileDto(
                        lp.getSkills(),
                        lp.getExperienceYears(),
                        lp.getPreferredCategories(),
                        lp.getExpectedDailyWage(),
                        lp.getAvailabilityStatus()
                ));
            });
        }

        dto.setAverageRating(reviewRepository.getAverageRatingForUser(user.getId()));
        dto.setTotalReviews(reviewRepository.countByTargetUserId(user.getId()));
        return dto;
    }
}
