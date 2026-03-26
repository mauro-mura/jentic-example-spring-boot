package dev.jentic.example.springboot.agent;

import dev.jentic.core.BehaviorType;
import dev.jentic.core.annotations.JenticAgent;
import dev.jentic.core.annotations.JenticBehavior;
import dev.jentic.runtime.agent.BaseAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Logs the current time every 5 seconds.
 * Discovered automatically because it is in the package configured
 * under jentic.agents.base-package in application.yml.
 */
@JenticAgent("clock-agent")
public class ClockAgent extends BaseAgent {

    private static final Logger log = LoggerFactory.getLogger(ClockAgent.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @JenticBehavior(type = BehaviorType.CYCLIC, interval = "5s")
    public void tick() {
        log.info("[ClockAgent] tick — {}", LocalTime.now().format(FMT));
    }
}