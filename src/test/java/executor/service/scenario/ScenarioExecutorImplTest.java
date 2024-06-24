package executor.service.scenario;



import executor.service.exception.SiteNotFoundException;
import executor.service.exception.step.UnknownStepException;
import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.scenario.step.StepExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Disabled
class ScenarioExecutorImplTest {
    private WebDriver webDriver;
    private ScenarioExecutor scenarioExecutor;

    private StepExecution stepExecution;

    @BeforeEach
    public void initEach() {
        stepExecution = mock(StepExecution.class);
        when(stepExecution.getStepAction()).thenReturn("clickCss");
        scenarioExecutor = new ScenarioExecutorImpl(List.of(stepExecution));
        webDriver = mock(WebDriver.class);
    }

    @Test
    public void testExecute() {
        var scenario = new Scenario("valid scenario", "https://some/url",
                List.of(new Step("clickCss", "do>something")));
        scenarioExecutor.execute(scenario, webDriver);
        verify(stepExecution, times(1)).step(any(WebDriver.class), any(Step.class));
    }

    @Test
    public void testExecute_shouldThrowSiteNotFoundException() {
        var scenario = new Scenario("scenario with bad url", "https://some/bad/url",
                List.of(new Step("clickCss", "do>something")));
        doThrow(WebDriverException.class).when(webDriver).get(anyString());
        assertThatThrownBy(() -> scenarioExecutor.execute(scenario, webDriver))
                .isInstanceOf(SiteNotFoundException.class);
    }

    @Test
    public void testExecute_shouldThrowUnknownStepException() {
        var scenario = new Scenario("scenario with bad steps", "https://some/bad/url",
                List.of(new Step("bad action", "do>something")));
        assertThatThrownBy(() -> scenarioExecutor.execute(scenario, webDriver))
                .isInstanceOf(UnknownStepException.class);
    }

}