package com.kazmiruk.travel_agency.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "TRAVEL AGENCY APPLICATION",
                description = "A program to support international travel agency business operations",
                version = "1.0",
                contact = @Contact(
                        name = "Dmytro Kazmiruk",
                        email = "dima.kazmiruk.05@gmail.com"
                )
        ),
        servers = @Server(
                description = "Local ENV",
                url = "http://localhost:8080"
        )
)
public class OpenApiConfig {
}
