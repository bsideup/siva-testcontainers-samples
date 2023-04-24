package com.sivalabs.demo;

import org.springframework.boot.SpringApplication;

public class TestApplication {

	public static void main(String[] args) {
		// TODO discuss spring-boot:run
		// TODO discuss @Configuration vs @TestConfiguration

		SpringApplication.from(SpringBootKafkaTestcontainersDemoApplication::main)
			.with(TestEnvironmentConfiguration.class)
			.run(args);
	}

}
