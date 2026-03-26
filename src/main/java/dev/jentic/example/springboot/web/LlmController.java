package dev.jentic.example.springboot.web;

import dev.jentic.runtime.JenticRuntime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates starting a Jentic agent with {@code autoStart = false} on demand.
 *
 * <p>The {@code LlmGreeterAgent} is registered at startup but not started.
 * This endpoint activates it, triggering its ONE_SHOT behavior which calls the LLM.
 */
@RestController
@RequestMapping("/api")
public class LlmController {

    private final JenticRuntime runtime;

    public LlmController(JenticRuntime runtime) {
        this.runtime = runtime;
    }

    /**
     * Start the LLM greeter agent. Check the application logs for the LLM response.
     *
     * <pre>
     *   curl -X POST http://localhost:8080/api/llm-greet
     * </pre>
     */
    @PostMapping("/llm-greet")
    public ResponseEntity<String> greet() {
        return runtime.getAgent("llm-greeter")
                .map(agent -> {
                    if (agent.isRunning()) {
                        return ResponseEntity.ok("LlmGreeterAgent is already running.");
                    }
                    agent.start();
                    return ResponseEntity.ok("LlmGreeterAgent started — check the logs for the LLM response.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}