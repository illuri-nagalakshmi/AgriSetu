package com.agrisetu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String uri = request.getRequestURI();
        String acceptHeader = request.getHeader("Accept");

        // If a browser is directly loading an HTML page, redirect to login or home
        if ((uri != null && uri.endsWith(".html")) ||
            (acceptHeader != null && acceptHeader.contains("text/html") && (uri == null || !uri.startsWith("/api/")))) {
            response.sendRedirect("/login.html");
            return;
        }

        // For API calls, return JSON 403
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", "Access denied: You do not have permission to access this resource.");

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
