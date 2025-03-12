package dev.notyouraverage.project.one.http.asynchronous_http_server.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI getOpenAPI(ServletContext servletContext) {
        Server server = new Server().url(servletContext.getContextPath());
        return new OpenAPI()
                .info(
                        new Info().title("Project 1: Asynchronous HTTP Server")
                                .version("1.0")
                                .description(
                                        "Service that accepts incoming requests and sends for asynchronous processing"
                                )
                )
                .servers(List.of(server));
    }
}
