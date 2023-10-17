package executor.service.execution.scenario;

import executor.service.execution.exception.SiteNotFoundException;
import executor.service.execution.exception.step.UnknownStepException;
import executor.service.execution.scenario.step.StepExecution;
import executor.service.model.Scenario;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


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
        Scenario scenario = new Scenario("valid scenario", "https://some/url", List.of(new Step("clickCss", "do>something")));
        scenarioExecutor.execute(scenario, webDriver);
        verify(stepExecution, times(1)).step(any(WebDriver.class), any(Step.class));
    }

    @Test
    public void testExecute_shouldThrowSiteNotFoundException() {
        Scenario scenario = new Scenario("scenario with bad url", "https://some/bad/url", List.of(new Step("clickCss", "do>something")));
        doThrow(WebDriverException.class).when(webDriver).get(anyString());
        assertThatThrownBy(() -> scenarioExecutor.execute(scenario, webDriver))
                .isInstanceOf(SiteNotFoundException.class);
    }

    @Test
    public void testExecute_shouldThrowUnknownStepException() {
        Scenario scenario = new Scenario("scenario with bad steps", "https://some/bad/url", List.of(new Step("bad action", "do>something")));
        assertThatThrownBy(() -> scenarioExecutor.execute(scenario, webDriver))
                .isInstanceOf(UnknownStepException.class);
    }

}