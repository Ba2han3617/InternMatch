package com.example.internmatch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("InternMatch API")
                        .version("1.0.0")
                        .description("InternMatch: Staj Uygunluk Oranı Ölçme Sistemi REST API Dokümantasyonu")
                        .contact(new Contact()
                                .name("InternMatch Team")
                                .email("support@internmatch.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
