package org.goormton.darktourism;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@OpenAPIDefinition
@SpringBootApplication
public class DarktourismApplication {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DarktourismApplication.class, args);
	}

}
