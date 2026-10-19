package com.agrisetu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(RestAuthenticationEntryPoint authenticationEntryPoint,
                          RestAccessDeniedHandler accessDeniedHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/auth/**")
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .authorizeHttpRequests(auth -> auth
                // Public Static Web Pages
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/login.html",
                    "/register.html",
                    "/jobs.html",
                    "/job-details.html",
                    "/about.html",
                    "/privacy.html",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()

                // Public REST APIs
                .requestMatchers(
                    "/api/auth/register",
                    "/api/auth/login",
                    "/api/auth/logout",
                    "/api/auth/current"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/jobs", "/api/jobs/{id:[0-9]+}", "/api/jobs/featured").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews/user/**").permitAll()

                // Farmer Specific Pages & APIs
                .requestMatchers(
                    "/farmer-dashboard.html",
                    "/create-job.html",
                    "/manage-jobs.html",
                    "/applications.html"
                ).hasAnyRole("FARMER", "ADMIN")
                .requestMatchers(
                    "/api/jobs/farmer/**",
                    "/api/applications/job/**",
                    "/api/applications/*/decision"
                ).hasAnyRole("FARMER", "ADMIN")

                // Labourer Specific Pages & APIs
                .requestMatchers(
                    "/labourer-dashboard.html",
                    "/my-applications.html",
                    "/saved-jobs.html"
                ).hasAnyRole("LABOURER", "ADMIN")
                .requestMatchers(
                    "/api/applications/apply",
                    "/api/applications/my",
                    "/api/saved-jobs/**"
                ).hasAnyRole("LABOURER", "ADMIN")

                // Admin Pages & APIs
                .requestMatchers(
                    "/admin-dashboard.html",
                    "/admin-users.html",
                    "/admin-jobs.html",
                    "/admin-reports.html",
                    "/api/admin/**"
                ).hasRole("ADMIN")

                // Authenticated General Pages & APIs
                .requestMatchers(
                    "/assignments.html",
                    "/profile.html",
                    "/notifications.html",
                    "/api/users/**",
                    "/api/assignments/**",
                    "/api/notifications/**",
                    "/api/reviews/**",
                    "/api/reports/**"
                ).authenticated()

                .anyRequest().authenticated()
            );

        return http.build();
    }
}
