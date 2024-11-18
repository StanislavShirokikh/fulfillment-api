package ru.shirokikh.fulfillmentapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product API",
                version = "1.0",
                description = "API для управления продуктами",
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        )
)
public class SwaggerConfig {
}
