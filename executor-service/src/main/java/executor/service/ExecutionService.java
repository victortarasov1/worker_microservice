package executor.service;

import executor.service.maintenance.plugin.proxy.ScenarioExecutor;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                 ScenarioExecutor scenarioExecutor);
}
