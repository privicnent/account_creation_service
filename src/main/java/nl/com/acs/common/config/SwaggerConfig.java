package nl.com.acs.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(info()).addSecurityItem(new SecurityRequirement().addList("basicAuth")).components(
                new Components().addSecuritySchemes("basicAuth",
                        new SecurityScheme().name("basicAuth").type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
    private Info info() {
        return new Info().title("Register and Login Service API")
                .description("This service handles customer registration, account mapping, and user authentication")
                .version("1.0.0");
    }

}
