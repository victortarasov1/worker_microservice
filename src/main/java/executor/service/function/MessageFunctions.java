package executor.service.function;

import executor.service.factory.WebDriverProvider;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import executor.service.scenario.ScenarioExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {
    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<Scenario, ScenarioReport> execute(ScenarioExecutor executor, WebDriverProvider provider) {
        return scenario -> {
            logger.info("Received scenario with ID: {}", scenario.getId());
            var webDriver = provider.create();
            var scenarioReport = executor.execute(scenario, webDriver);
            webDriver.quit();
            return scenarioReport;
        };
    }
}
