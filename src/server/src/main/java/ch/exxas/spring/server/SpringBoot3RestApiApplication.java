package ch.exxas.spring.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Exxas Server API",
        version = "1.0",
        description = "Spring Boot 3 REST API"
    )
)
@SpringBootApplication
public class SpringBoot3RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3RestApiApplication.class, args);
	}

}
