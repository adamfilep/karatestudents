package com.example.karatestudents;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Karate Students Manager",
				version = "1.0.0",
				description = "Documentation for exam project: Karate students manager"
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Local server"),
				@Server(url = "http://localhost:80", description = "Docker")
		}
)
@SpringBootApplication
public class KaratestudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaratestudentsApplication.class, args);
	}

}
