package com.agrisetu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080", "http://127.0.0.1:8080",
                                "http://localhost:5500", "http://127.0.0.1:5500",
                                "http://localhost:3000", "null")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from frontend/ folder so the full application is directly accessible at http://localhost:8080/
        registry.addResourceHandler("/**")
                .addResourceLocations(
                        "classpath:/static/",
                        "file:../frontend/",
                        "file:frontend/",
                        "file:./frontend/"
                );
    }
}
