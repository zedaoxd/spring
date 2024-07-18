package br.com.bruno.bff.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.openapi.title}") String title,
            @Value("${springdoc.openapi.version}") String version,
            @Value("${springdoc.openapi.description}") String description,
            @Value("${springdoc.openapi.contact.name}") String contactName,
            @Value("${springdoc.openapi.contact.email}") String contactEmail) {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)));
    }

}
