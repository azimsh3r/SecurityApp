package de.akhmadjonov.project.FirstSecurityApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

@SpringBootApplication
public class FirstSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSecurityAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
