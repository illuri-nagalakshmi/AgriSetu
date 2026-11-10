package com.agrisetu.service;

import com.agrisetu.dto.AuthResponse;
import com.agrisetu.dto.LoginRequest;
import com.agrisetu.dto.RegisterRequest;
import com.agrisetu.entity.*;
import com.agrisetu.exception.BadRequestException;
import com.agrisetu.exception.UnauthorizedException;
import com.agrisetu.repository.FarmerProfileRepository;
import com.agrisetu.repository.LabourerProfileRepository;
import com.agrisetu.repository.UserRepository;
import com.agrisetu.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final LabourerProfileRepository labourerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityUtils securityUtils;
    private final SecurityContextRepository securityContextRepository;
    private final CookieCsrfTokenRepository csrfTokenRepository;

    public AuthService(UserRepository userRepository,
                       FarmerProfileRepository farmerProfileRepository,
                       LabourerProfileRepository labourerProfileRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       SecurityUtils securityUtils,
                       SecurityContextRepository securityContextRepository,
                       CookieCsrfTokenRepository csrfTokenRepository) {
        this.userRepository = userRepository;
        this.farmerProfileRepository = farmerProfileRepository;
        this.labourerProfileRepository = labourerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.securityUtils = securityUtils;
        this.securityContextRepository = securityContextRepository;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request, HttpServletRequest httpRequest) {
        return register(request, httpRequest, null);
    }

    @Transactional
    public AuthResponse register(RegisterRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Password and confirm password do not match");
        }

        if (request.getRole() == Role.ADMIN) {
            throw new BadRequestException("Self-registration as Administrator is not permitted");
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail().trim())) {
            throw new BadRequestException("An account with email " + request.getEmail() + " already exists");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber().trim())) {
            throw new BadRequestException("An account with phone number " + request.getPhoneNumber() + " already exists");
        }

        User user = new User(
                request.getFullName().trim(),
                request.getEmail().trim().toLowerCase(),
                request.getPhoneNumber().trim(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole(),
                request.getPreferredLanguage() != null ? request.getPreferredLanguage() : "en",
                request.getVillage().trim(),
                request.getDistrict().trim(),
                request.getState().trim()
        );
        user = userRepository.save(user);

        if (user.getRole() == Role.FARMER) {
            FarmerProfile fp = new FarmerProfile(
                    user,
                    request.getFarmName(),
                    request.getTotalLandAcres(),
                    request.getPrimaryCrops()
            );
            farmerProfileRepository.save(fp);
        } else if (user.getRole() == Role.LABOURER) {
            LabourerProfile lp = new LabourerProfile(
                    user,
                    request.getSkills(),
                    request.getExperienceYears() != null ? request.getExperienceYears() : 0,
                    request.getPreferredCategories(),
                    request.getExpectedDailyWage()
            );
            labourerProfileRepository.save(lp);
        }

        // Auto-login upon successful registration
        authenticateAndSetSession(user.getEmail(), request.getPassword(), httpRequest, httpResponse);

        return new AuthResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getPreferredLanguage(),
                user.getVillage(),
                user.getDistrict(),
                user.getState(),
                "Registration successful"
        );
    }

    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        return login(request, httpRequest, null);
    }

    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
            );

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            if (httpResponse != null) {
                securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);
                CsrfToken csrfToken = csrfTokenRepository.generateToken(httpRequest);
                csrfTokenRepository.saveToken(csrfToken, httpRequest, httpResponse);
            } else {
                HttpSession session = httpRequest.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            }

            User user = userRepository.findByEmailIgnoreCase(request.getEmail().trim())
                    .orElseThrow(() -> new UnauthorizedException("User not found"));

            return new AuthResponse(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getRole(),
                    user.getPreferredLanguage(),
                    user.getVillage(),
                    user.getDistrict(),
                    user.getState(),
                    "Login successful"
            );
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid email or password");
        }
    }

    public void logout(HttpServletRequest httpRequest) {
        logout(httpRequest, null);
    }

    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        SecurityContextHolder.clearContext();
        if (httpResponse != null) {
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            securityContextRepository.saveContext(emptyContext, httpRequest, httpResponse);
        }
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public AuthResponse getCurrentUser() {
        return securityUtils.getCurrentUserOptional()
                .map(user -> new AuthResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getRole(),
                        user.getPreferredLanguage(),
                        user.getVillage(),
                        user.getDistrict(),
                        user.getState(),
                        "Current user session active"
                ))
                .orElse(null);
    }

    private void authenticateAndSetSession(String email, String rawPassword, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword)
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        if (httpResponse != null) {
            securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);
            CsrfToken csrfToken = csrfTokenRepository.generateToken(httpRequest);
            csrfTokenRepository.saveToken(csrfToken, httpRequest, httpResponse);
        } else {
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        }
    }
}
