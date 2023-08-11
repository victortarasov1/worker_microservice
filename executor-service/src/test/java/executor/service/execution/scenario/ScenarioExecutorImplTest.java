package executor.service.execution.scenario;

import executor.service.model.ScenarioDto;
import executor.service.execution.scenario.step.ClickCss;
import executor.service.execution.scenario.step.ClickXpath;
import executor.service.execution.scenario.step.Sleep;
import executor.service.execution.scenario.step.StepExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;


class ScenarioExecutorImplTest {
    private WebDriver webDriver;
    private ScenarioExecutorImpl scenarioExecutorImpl;
    private ScenarioDto scenarioDto;

    @BeforeEach
    public void initEach() {
        List<StepExecution> stepsExecution = new ArrayList<>();
        stepsExecution.add(new ClickXpath());
        stepsExecution.add(new ClickCss());
        stepsExecution.add(new Sleep());
        scenarioExecutorImpl = new ScenarioExecutorImpl(stepsExecution);
        webDriver = Mockito.mock(WebDriver.class);
        scenarioDto = Mockito.mock(ScenarioDto.class);
    }

    @Test
    public void testExecute() {
        scenarioExecutorImpl.execute(scenarioDto, webDriver);
        webDriver.quit();
    }
}