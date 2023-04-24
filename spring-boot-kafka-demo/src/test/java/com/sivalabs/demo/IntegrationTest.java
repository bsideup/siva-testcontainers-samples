package com.sivalabs.demo;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = TestEnvironmentConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.profiles.active=it",
        }
)
public interface IntegrationTest {
}
