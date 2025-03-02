package com.chaoticteam.backend.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "Chaotic Team API",
        version = "1.0.0",
        description = "API de Chaotic Team",
        contact = @Contact(
            name = "Alex Diaz",
            email = "alexleonel96@hotmail.com"
        ),
        license = @License(
            name = "MIT",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Servidor local"
        )
    },
    security = {
        @SecurityRequirement(
            name = "bearerAuth"
        )
    }

)
@SecurityScheme(
    name = "bearerAuth",
    description = "Token de autenticación JWT",
    type = SecuritySchemeType.HTTP,
    paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER, // or SecuritySchemeIn.Cookie
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

}
