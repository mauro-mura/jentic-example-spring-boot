package dev.jentic.example.springboot.agent;

import dev.jentic.core.annotations.JenticAgent;
import dev.jentic.core.annotations.JenticBehavior;
import dev.jentic.core.BehaviorType;
import dev.jentic.core.llm.LLMProvider;
import dev.jentic.core.llm.LLMRequest;
import dev.jentic.runtime.agent.BaseAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LLM-powered agent that sends a greeting prompt when explicitly started.
 *
 * <p>Declared with {@code autoStart = false}: registered at startup but dormant
 * until activated via:
 * <pre>
 *   POST http://localhost:8080/api/llm-greet
 * </pre>
 *
 * <p>The {@link LLMProvider} is injected by {@code AgentFactory} when the runtime
 * has one registered (i.e. {@code jentic.llm.provider} is set to a non-{@code none}
 * value in {@code application.yml}). Without a provider, the behavior logs a warning
 * and skips the LLM call. This allows the app to start cleanly with LLM disabled.
 *
 * <p>To enable:
 * <ol>
 *   <li>Uncomment {@code jentic-adapters} in {@code pom.xml}</li>
 *   <li>Uncomment the {@code jentic.llm} block in {@code application.yml}</li>
 *   <li>{@code POST /api/llm-greet}</li>
 * </ol>
 */
@JenticAgent(value = "llm-greeter", autoStart = false)
public class LlmGreeterAgent extends BaseAgent {

    private static final Logger log = LoggerFactory.getLogger(LlmGreeterAgent.class);

    private final LLMProvider provider;

    /**
     * Used by AgentFactory when a LLMProvider service is registered in the runtime
     * (jentic.llm.provider != none). AgentFactory prefers the most-parameter constructor.
     */
    public LlmGreeterAgent(LLMProvider provider) {
        super("llm-greeter", "LLM Greeter");
        this.provider = provider;
    }

    /**
     * Fallback used by AgentFactory when no LLMProvider is registered.
     * The agent is still discoverable and startable, but greet() will be a no-op.
     */
    public LlmGreeterAgent() {
        super("llm-greeter", "LLM Greeter");
        this.provider = null;
    }

    @JenticBehavior(type = BehaviorType.ONE_SHOT)
    public void greet() {
        if (provider == null) {
            log.warn("[LlmGreeterAgent] No LLMProvider configured. " +
                     "Uncomment jentic-adapters in pom.xml and jentic.llm in application.yml.");
            return;
        }

        log.info("[LlmGreeterAgent] Calling {} ...", provider.getProviderName());

        LLMRequest request = LLMRequest.builder(provider.getProviderName())
                .systemMessage("You are a friendly assistant embedded in a Jentic multi-agent system.")
                .userMessage("Say hello in one sentence, mentioning that you are running inside " +
                             "a Spring Boot app powered by Jentic.")
                .maxTokens(100)
                .build();

        String reply = provider.chat(request).join().content();
        log.info("[LlmGreeterAgent] LLM says: {}", reply);
    }
}