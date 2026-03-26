package dev.jentic.example.springboot.agent;

import dev.jentic.core.annotations.JenticAgent;
import dev.jentic.core.annotations.JenticBehavior;
import dev.jentic.core.BehaviorType;
import dev.jentic.core.annotations.JenticMessageHandler;
import dev.jentic.core.Message;
import dev.jentic.runtime.agent.BaseAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints a greeting on startup (ONE_SHOT) and handles messages on "greet.topic".
 */
@JenticAgent("greeter-agent")
public class GreeterAgent extends BaseAgent {

    private static final Logger log = LoggerFactory.getLogger(GreeterAgent.class);

    @JenticBehavior(type = BehaviorType.ONE_SHOT)
    public void greetOnStartup() {
        log.info("[GreeterAgent] Hello from Jentic Spring Boot Starter!");
    }

    @JenticMessageHandler("greet.topic")
    public void onGreetMessage(Message message) {
        log.info("[GreeterAgent] received greeting: {}", message.content());
    }
}