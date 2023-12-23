package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenAPIConfig {
    @Value("${local.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("mayurmlomate@gmail.com");
        contact.setName("Mayur Lomate");

        Info info = new Info()
                .title("Player Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage players.");

        return new OpenAPI().info(info).servers(Arrays.asList(devServer));
    }
}
