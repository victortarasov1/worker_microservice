package executor.service.exectuion;

import executor.service.exectuion.scenario.ScenarioExecutor;
import executor.service.maintenance.ScenarioSourceListener;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                 ScenarioExecutor scenarioExecutor);
}
