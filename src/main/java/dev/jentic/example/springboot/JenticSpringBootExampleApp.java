package dev.jentic.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Minimal Spring Boot application showing jentic-spring-boot-starter in action.
 *
 * <p>No configuration class, no manual bean wiring. {@code JenticRuntime} is
 * started automatically by the auto-configuration. Agents in the
 * {@code dev.jentic.example.springboot.agent} package are discovered at startup.
 *
 * <p>Run with:
 * <pre>
 *   mvn spring-boot:run -pl jentic-example-spring-boot
 * </pre>
 *
 * <p>Then check:
 * <pre>
 *   curl http://localhost:8080/actuator/health
 * </pre>
 */
@SpringBootApplication
public class JenticSpringBootExampleApp {

    public static void main(String[] args) {
        SpringApplication.run(JenticSpringBootExampleApp.class, args);
    }
}
