package executor.service.function;

import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {
    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<Scenario, ScenarioReport> execute() {
        return scenario -> {
            logger.info("ddd");
            return new ScenarioReport();
        };
    }
}
