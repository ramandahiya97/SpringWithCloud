package com.rest.webservices.spring_with_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SpringWithCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithCloudApplication.class, args);
	}

}
