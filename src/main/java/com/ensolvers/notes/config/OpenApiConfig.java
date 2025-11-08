package com.ensolvers.notes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI notesOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Notes API")
            .description("API para crear, editar, archivar y filtrar notas")
            .version("v1")
            .contact(new Contact().name("Ensolvers Exercise")))
        .servers(List.of(new Server().url("http://localhost:8080").description("Local Dev")));
  }
}
