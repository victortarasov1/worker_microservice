package executor.service;

import executor.service.model.ScenarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.mockito.Mockito.*;

class ScenarioListenerTest {
    private ScenarioExecutor scenarioExecutorMock;
    private WebDriver webDriverMock;
    private ScenarioSource scenarioSourceMock;
    private ScenarioDto scenarioDtoMock;
    private ScenarioListener scenarioListener;

    @BeforeEach
    void init() {
        scenarioExecutorMock = mock(ScenarioExecutor.class);
        webDriverMock = mock(WebDriver.class);
        scenarioSourceMock= mock(ScenarioSource.class);
        scenarioDtoMock = mock(ScenarioDto.class);

        scenarioListener = new ScenarioListener(scenarioExecutorMock, webDriverMock, scenarioSourceMock);
    }

    @Test
    void testExecuteIfThereAreNoScenarios() {
        doReturn(List.of()).when(scenarioSourceMock).getScenarios();
        scenarioListener.execute();

        verify(scenarioExecutorMock, times(0)).execute(scenarioDtoMock, webDriverMock);
    }

    @Test
    void testExecuteIfThereAreTwoScenarios() {
        doReturn(List.of(scenarioDtoMock, scenarioDtoMock)).when(scenarioSourceMock).getScenarios();
        scenarioListener.execute();

        verify(scenarioExecutorMock, times(2)).execute(scenarioDtoMock, webDriverMock);
    }
}
