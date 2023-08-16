package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.execution.scenario.ScenarioExecutorImpl;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class ExecutionServiceImplTest {

    private ExecutionService executionService;

    private WebDriver webDriver;

    private ScenarioSourceQueueHandler scenarios;

    private ScenarioExecutor scenarioExecutor;


    @BeforeEach
    void setUp() {
        this.executionService = Mockito.mock(ExecutionServiceImpl.class);
        this.webDriver = Mockito.mock(ChromeDriver.class);
        this.scenarios = Mockito.mock(ScenarioSourceQueueHandler.class);
        this.scenarioExecutor = Mockito.mock(ScenarioExecutorImpl.class);
    }


    @Test
    void testSuccessfulExecution() {
        executionService.execute(webDriver, scenarios, scenarioExecutor);
        Mockito.verify(executionService, Mockito.times(1))
                .execute(webDriver, scenarios, scenarioExecutor);
    }

    @Test
    void testFailureExecution() {
        String errorMessage = "Execution failed";
        Mockito.doThrow(RuntimeException.class).when(executionService)
                .execute(webDriver, scenarios, scenarioExecutor);
        Assertions.assertThrows(RuntimeException.class,
                () -> executionService.execute(webDriver, scenarios, scenarioExecutor),
                errorMessage);
    }


}