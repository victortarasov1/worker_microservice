package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.Queue;

/**
 * The ExecutionService interface defines a facade for managing the execution of scenarios
 * using a WebDriver and a scenario queue handler.
 *
 */
public interface ExecutionService {

    /**
     * Initiates the execution of scenarios using the provided WebDriver, scenario source queue handler,
     * and scenario executor.
     *
     * @param webDriver           The WebDriver instance used for browser automation during scenario execution.
     * @param scenarios           The queue of scenarios.
     * @param scenarioExecutor    The scenario executor responsible for executing individual scenarios.
     */
    void execute(WebDriver webDriver, Queue<Scenario> scenarios,
                 ScenarioExecutor scenarioExecutor);
}