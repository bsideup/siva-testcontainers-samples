package com.sivalabs.demo;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

// TODO discuss ContextConfiguration-like annotation (@EnvironmentConfiguration?)
// TODO ask why @RestartScope doesn't work on class
@TestConfiguration
public class TestEnvironmentConfiguration {

    // TODO Discuss meta annotation (@DevServiceBean?)
    // TODO discuss not having automatic Startable#stop
    @Bean
    @RestartScope
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15.2-alpine")
                .withClasspathResourceMapping(
                        "sql/schema.sql",
                        "/docker-entrypoint-initdb.d/schema.sql",
                        BindMode.READ_ONLY
                );
    }

    @Bean
    @RestartScope
    @ServiceConnection
    /* TODO discuss
    @PropertiesContributor({
            "spring.kafka.bootstrap-servers=#getBootstrapServers()"
    })
     */
    KafkaContainer kafkaContainer() {
        return new KafkaContainer(
                DockerImageName.parse("confluentinc/cp-kafka:7.2.1")
        );
    }
}
