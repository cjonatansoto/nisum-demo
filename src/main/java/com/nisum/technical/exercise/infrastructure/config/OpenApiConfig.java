package com.nisum.technical.exercise.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.version:1.0}")
    private String appVersion;

    @Value("${app.title:Nisun API}")
    private String apiName;

    @Value("${app.description:Demo API de usuarios}")
    private String apiDescripcion;

    @Value("${app.contact.name:Líder Técnico}")
    private String contactName;

    @Value("${app.contact.email:contacto@email.com}")
    private String contactEmail;

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiName)
                        .version(appVersion)
                        .description(apiDescripcion)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
