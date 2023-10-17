package executor.service.execution.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.collection.queue.scenario.ScenarioSourceQueueHandler;
import org.openqa.selenium.WebDriver;

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
     * @param scenarios           The scenario source queue handler responsible for managing the queue of scenarios.
     * @param scenarioExecutor    The scenario executor responsible for executing individual scenarios.
     */
    void execute(WebDriver webDriver, ScenarioSourceQueueHandler scenarios,
                 ScenarioExecutor scenarioExecutor);
}