package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
import executor.service.queue.consumer.scenario.ScenarioConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExecutionServiceImplTest {

    private ExecutionService executionService;

    private WebDriver webDriver;

    private ScenarioExecutor scenarioExecutor;
    private ScenarioConsumer listener;


    @BeforeEach
    void setUp() {
        executionService = new ExecutionServiceImpl();
        webDriver = mock(ChromeDriver.class);
        scenarioExecutor = mock(ScenarioExecutor.class);
        listener = mock(ScenarioConsumer.class);
    }


    @Test
    void testExecute() {
        var scenario = new Scenario("name", "site", List.of());
        when(listener.poll()).thenReturn(scenario, null);
        executionService.execute(webDriver, listener, scenarioExecutor);
        verify(scenarioExecutor, times(1)).execute(any(Scenario.class), any(WebDriver.class));
        verify(webDriver, times(1)).close();
    }

}