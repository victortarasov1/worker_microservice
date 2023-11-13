package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
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


    @BeforeEach
    void setUp() {
        executionService = new ExecutionServiceImpl();
        webDriver = mock(ChromeDriver.class);
        scenarioExecutor = mock(ScenarioExecutor.class);

    }


    @Test
    void testExecute() {
        var scenarios = new ConcurrentLinkedQueue<>(List.of(new Scenario("name", "site", List.of())));
        executionService.execute(webDriver, scenarios, scenarioExecutor);
        verify(scenarioExecutor, times(1)).execute(any(Scenario.class), any(WebDriver.class));
        verify(webDriver, times(1)).close();
    }

}