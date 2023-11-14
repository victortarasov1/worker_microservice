package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
import executor.service.redis.queue.listener.scenario.ScenarioQueueListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExecutionServiceImplTest {

    private ExecutionService executionService;

    private WebDriver webDriver;

    private ScenarioExecutor scenarioExecutor;
    private ScenarioQueueListener listener;


    @BeforeEach
    void setUp() {
        executionService = new ExecutionServiceImpl();
        webDriver = mock(ChromeDriver.class);
        scenarioExecutor = mock(ScenarioExecutor.class);
        listener = mock(ScenarioQueueListener.class);
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