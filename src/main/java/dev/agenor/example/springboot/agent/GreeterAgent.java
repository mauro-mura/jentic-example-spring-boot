package dev.agenor.example.springboot.agent;

import dev.agenor.core.annotations.Agent;
import dev.agenor.core.annotations.Behavior;
import dev.agenor.core.BehaviorType;
import dev.agenor.core.annotations.AgenorMessageHandler;
import dev.agenor.core.Message;
import dev.agenor.runtime.agent.BaseAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints a greeting on startup (ONE_SHOT) and handles messages on "greet.topic".
 */
@Agent("greeter-agent")
public class GreeterAgent extends BaseAgent {

    private static final Logger log = LoggerFactory.getLogger(GreeterAgent.class);

    @Behavior(type = BehaviorType.ONE_SHOT)
    public void greetOnStartup() {
        log.info("[GreeterAgent] Hello from Agenor Spring Boot Starter!");
    }

    @AgenorMessageHandler("greet.topic")
    public void onGreetMessage(Message message) {
        log.info("[GreeterAgent] received greeting: {}", message.content());
    }
}