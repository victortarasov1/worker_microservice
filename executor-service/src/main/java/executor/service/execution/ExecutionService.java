package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.maintenance.ScenarioSourceListener;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                 ScenarioExecutor scenarioExecutor);
}
