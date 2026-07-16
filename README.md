# agenor-example-spring-boot

Minimal standalone Spring Boot 4.0.x application demonstrating
[agenor-spring-boot-starter](https://github.com/mauro-mura/agenor) zero-boilerplate integration.

## Prerequisites

- Java 21+
- Maven 3.9+
- Agenor installed locally (until published to Maven Central):

```bash
git clone --branch v0.24.0 https://github.com/mauro-mura/agenor.git
cd agenor
mvn install -DskipTests
```

## Run

```bash
git clone https://github.com/mauro-mura/agenor-example-spring-boot.git
cd agenor-example-spring-boot
mvn spring-boot:run
```

Expected output:
```
INFO  AgenorAutoConfiguration - Building AgenorRuntime: runtime.name=example-runtime
INFO  AgenorAutoConfiguration - Starting AgenorRuntime...
INFO  GreeterAgent            - [GreeterAgent] Hello from Agenor Spring Boot Starter!
INFO  ClockAgent              - [ClockAgent] tick — 10:00:00
INFO  ClockAgent              - [ClockAgent] tick — 10:00:05
```

## What it shows

| Agent | autoStart | Behavior | Description |
|---|---|---|---|
| `ClockAgent` | true | `CYCLIC` every 5s | Logs the current time automatically |
| `GreeterAgent` | true | `ONE_SHOT` + message handler | Greets on startup; handles messages on `greet.topic` |
| `LlmGreeterAgent` | **false** | `ONE_SHOT` | Dormant at startup; activated on demand via REST |

Zero `@Configuration` classes. Zero manual bean wiring. One dependency.

## Check health

```bash
curl http://localhost:8080/actuator/health | jq
```

```json
{
  "status": "UP",
  "components": {
    "Agenor": {
      "status": "UP",
      "details": {
        "runtime.name": "example-runtime",
        "agents.total": 3,
        "agents.running": 2
      }
    }
  }
}
```

`agents.total=3` (all three registered), `agents.running=2` (`LlmGreeterAgent` is dormant).

## Enable LLM and activate LlmGreeterAgent

**Step 1** — uncomment `agenor-adapters` in `pom.xml`:
```xml
<dependency>
    <groupId>dev.agenor</groupId>
    <artifactId>agenor-adapters</artifactId>
    <version>${agenor.version}</version>
</dependency>
```

**Step 2** — uncomment the `llm` block in `application.yml`:
```yaml
agenor:
  llm:
    provider: openai
    api-key: ${OPENAI_API_KEY}
    model: gpt-4o-mini
```

**Step 3** — run with the API key:
```bash
OPENAI_API_KEY=sk-... mvn spring-boot:run
```

**Step 4** — trigger the LLM agent:
```bash
curl -X POST http://localhost:8080/api/llm-greet
```

```
LlmGreeterAgent started — check the logs for the LLM response.
```

Log output:
```
INFO  LlmGreeterAgent - [LlmGreeterAgent] Calling gpt-4o-mini ...
INFO  LlmGreeterAgent - [LlmGreeterAgent] LLM says: Hello! I'm running inside a Spring Boot app powered by Agenor, ready to assist you!
```