package org.goormton.darktourism;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@OpenAPIDefinition
@SpringBootApplication
public class DarktourismApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarktourismApplication.class, args);
	}

}
