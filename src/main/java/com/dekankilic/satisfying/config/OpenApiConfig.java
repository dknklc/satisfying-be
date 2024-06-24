package com.dekankilic.satisfying.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Satisfying App REST API Documentation")
                        .version("1.0")
                        .description("Food Delivery APP Documentation")
                        .contact(new Contact()
                                .name("Dekan KILIÇ")
                                .email("dekan.kilic@gmail.com")
                                .url("https://github.com/dknklc")));
    }
}
