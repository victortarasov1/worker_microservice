package executor.service.function;

import executor.service.facade.WorkerFacade;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    @Bean
    public Function<Scenario, ScenarioReport> execute(WorkerFacade facade) {
       return facade::execute;
    }
}
