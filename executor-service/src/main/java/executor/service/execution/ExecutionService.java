package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioSourceQueueHandler scenarios,
                 ScenarioExecutor scenarioExecutor);
}
