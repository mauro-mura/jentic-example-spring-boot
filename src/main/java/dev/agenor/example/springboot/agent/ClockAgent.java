package dev.agenor.example.springboot.agent;

import dev.agenor.core.BehaviorType;
import dev.agenor.core.annotations.Agent;
import dev.agenor.core.annotations.Behavior;
import dev.agenor.runtime.agent.BaseAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Logs the current time every 5 seconds.
 * Discovered automatically because it is in the package configured
 * under agenor.agents.base-package in application.yml.
 */
@Agent("clock-agent")
public class ClockAgent extends BaseAgent {

    private static final Logger log = LoggerFactory.getLogger(ClockAgent.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Behavior(type = BehaviorType.CYCLIC, interval = "5s")
    public void tick() {
        log.info("[ClockAgent] tick — {}", LocalTime.now().format(FMT));
    }
}