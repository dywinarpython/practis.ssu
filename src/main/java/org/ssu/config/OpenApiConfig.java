package org.ssu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI materialInventoryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Material Inventory API")
                        .description("REST API for warehouse, responsible persons, material values, movements and transfers.")
                        .version("0.0.1-SNAPSHOT"))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local server")));
    }
}
