package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;

import executor.service.model.ScenarioDto;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void execute(WebDriver webDriver, ScenarioSourceQueueHandler scenarios, ScenarioExecutor scenarioExecutor) {
        Optional<ScenarioDto> scenario = scenarios.poll();
        while (scenario.isPresent()) {
            scenarioExecutor.execute(scenario.get(), webDriver);
            scenario = scenarios.poll();
        }
        webDriver.close();
    }

}
