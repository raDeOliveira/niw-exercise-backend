package com.niw.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:4200") // Your Angular app URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Define allowed methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // This must be 'true' to include credentials
    }
}
