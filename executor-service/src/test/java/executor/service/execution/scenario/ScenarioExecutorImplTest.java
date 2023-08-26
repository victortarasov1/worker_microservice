package executor.service.execution.scenario;

import executor.service.exception.scenario.SiteNotFoundException;
import executor.service.exception.scenario.step.UnknownStepException;
import executor.service.execution.scenario.step.StepExecution;
import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
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
        ScenarioDto scenario = new ScenarioDto("valid scenario", "https://some/url", List.of(new StepDto("clickCss", "do>something")));
        scenarioExecutor.execute(scenario, webDriver);
        verify(stepExecution, times(1)).step(any(WebDriver.class), any(StepDto.class));
    }

    @Test
    public void testExecute_shouldThrowSiteNotFoundException() {
        ScenarioDto scenario = new ScenarioDto("scenario with bad url", "https://some/bad/url", List.of(new StepDto("clickCss", "do>something")));
        doThrow(WebDriverException.class).when(webDriver).get(anyString());
        assertThatThrownBy(() -> scenarioExecutor.execute(scenario, webDriver))
                .isInstanceOf(SiteNotFoundException.class);
    }

    @Test
    public void testExecute_shouldThrowUnknownStepException() {
        ScenarioDto scenario = new ScenarioDto("scenario with bad steps", "https://some/bad/url", List.of(new StepDto("bad action", "do>something")));
        assertThatThrownBy(() -> scenarioExecutor.execute(scenario, webDriver))
                .isInstanceOf(UnknownStepException.class);
    }

}