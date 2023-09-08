package tech.ada.bootcamp.cafe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI defaultOpenApiConfig(){
        return new OpenAPI()
                .info(new Info().title("CompraCafeService")
                        .description("Microserviço que permite a compra de café ")
                        .version("0.0.1"));
    }
}
