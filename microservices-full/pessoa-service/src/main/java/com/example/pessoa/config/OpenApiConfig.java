package com.example.pessoa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Pessoa Service API")
                .version("v1")
                .description("API de gestão de pessoas"));
    }
}
