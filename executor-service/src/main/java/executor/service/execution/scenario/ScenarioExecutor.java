package executor.service.execution.scenario;

import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

public interface ScenarioExecutor {
    /**
     * Executes the provided scenario using the given WebDriver.
     *
     * @param scenario The data transfer object representing the scenario to be executed.
     * @param webDriver   The WebDriver instance used for automating browser interactions during scenario execution.
     */
    void execute(Scenario scenario, WebDriver webDriver);
}
