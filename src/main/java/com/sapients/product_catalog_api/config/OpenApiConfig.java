package com.sapients.product_catalog_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private final Environment environment;

    public OpenApiConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        String serverUrl = "http://localhost:" + environment.getProperty("server.port");
        return new OpenAPI()
                .openapi("3.0.1")
                .info(new Info()
                        .title("Product Catalog API")
                        .version("1.0")
                        .description("API documentation for Product Catalog API")
                        .termsOfService("http://example.com/terms/")
                        .contact(new Contact()
                                .name("Support Team")
                                .url("http://example.com/contact")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url(serverUrl).description("Local server"),
                        new Server().url(serverUrl + "/h2-console").description("H2 Console")
                ));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}