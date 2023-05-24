package executor.service;

import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;


class ScenarioExecutorImplTest {
    private WebDriver webDriver;
    private ScenarioExecutorImpl scenarioExecutor;
    private ScenarioDto scenarioDto;

    @BeforeEach
    public void initEach() {
        scenarioExecutor = Mockito.mock(ScenarioExecutorImpl.class);
        webDriver = Mockito.mock(WebDriver.class);
        List<StepDto> steps = new ArrayList<>();
        steps.add(new StepDto("clickXpath", "xpath test"));
        steps.add(new StepDto("clickcss", "css test"));
        steps.add(new StepDto("sleep", "5"));
        scenarioDto = new ScenarioDto("test1", "site1", steps);
    }

    @Test
    public void testExecute() {
        scenarioExecutor.execute(scenarioDto, webDriver);
        webDriver.quit();
    }
}