package com.ensolvers.notes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  // Coma-separadas. En prod agregás el dominio real del front.
  @Value("${app.cors.allowed-origins:http://localhost:4200}")
  private String allowedOriginsCsv;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(allowedOriginsCsv.split("\\s*,\\s*"))
        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
