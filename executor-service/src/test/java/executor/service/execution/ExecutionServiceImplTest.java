package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.execution.scenario.ScenarioExecutorImpl;
import executor.service.model.ScenarioDto;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExecutionServiceImplTest {

    private ExecutionService executionService;

    private WebDriver webDriver;

    private ScenarioSourceQueueHandler scenarios;

    private ScenarioExecutor scenarioExecutor;


    @BeforeEach
    void setUp() {
        executionService = new ExecutionServiceImpl();
        webDriver = mock(ChromeDriver.class);
        scenarios = mock(ScenarioSourceQueueHandler.class);
        scenarioExecutor = mock(ScenarioExecutorImpl.class);
    }


    @Test
    void testExecute() {
        ScenarioDto scenario = new ScenarioDto();
        when(scenarios.poll()).thenReturn(Optional.of(scenario)).thenReturn(Optional.of(scenario)).thenReturn(Optional.empty());
        executionService.execute(webDriver, scenarios, scenarioExecutor);
        verify(scenarioExecutor, times(2)).execute(any(ScenarioDto.class), any(WebDriver.class));
        verify(webDriver, times(1)).close();
        verify(scenarios, times(3)).poll();
    }

}