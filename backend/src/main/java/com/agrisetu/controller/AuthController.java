package com.agrisetu.controller;

import com.agrisetu.dto.ApiResponse;
import com.agrisetu.dto.AuthResponse;
import com.agrisetu.dto.LoginRequest;
import com.agrisetu.dto.RegisterRequest;
import com.agrisetu.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        AuthResponse response = authService.register(request, httpRequest, httpResponse);
        return ResponseEntity.ok(ApiResponse.success("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        AuthResponse response = authService.login(request, httpRequest, httpResponse);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        authService.logout(httpRequest, httpResponse);
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<AuthResponse>> getCurrentUser() {
        AuthResponse response = authService.getCurrentUser();
        if (response == null) {
            return ResponseEntity.ok(new ApiResponse<>(false, "No active session", null));
        }
        return ResponseEntity.ok(ApiResponse.success("Session active", response));
    }
}
