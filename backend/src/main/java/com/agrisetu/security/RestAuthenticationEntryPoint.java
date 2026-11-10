package com.agrisetu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String uri = request.getRequestURI();
        String acceptHeader = request.getHeader("Accept");

        // If a browser is directly loading an HTML page, redirect to the login page
        if ((uri != null && uri.endsWith(".html")) ||
            (acceptHeader != null && acceptHeader.contains("text/html") && (uri == null || !uri.startsWith("/api/")))) {
            String redirectUrl = "/login.html";
            if (uri != null && !uri.contains("login.html") && !uri.equals("/")) {
                redirectUrl += "?redirect=" + URLEncoder.encode(uri, StandardCharsets.UTF_8);
            }
            response.sendRedirect(redirectUrl);
            return;
        }

        // For API calls, return JSON 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", "Authentication required. Please log in.");

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
