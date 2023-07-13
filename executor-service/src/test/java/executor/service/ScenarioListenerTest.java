package executor.service;

import executor.service.model.ScenarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class ScenarioListenerTest {
    private ScenarioSource scenarioSourceMock;
    private ScenarioDto scenarioDtoMock;
    private ScenarioListener scenarioListener;

    @BeforeEach
    void init() {
        scenarioSourceMock= mock(ScenarioSource.class);
        scenarioDtoMock = mock(ScenarioDto.class);

        scenarioListener = new ScenarioListener(scenarioSourceMock);
    }

    @Test
    void testGetScenarioIfThereAreNoScenarios() {
        doReturn(List.of()).when(scenarioSourceMock).getScenarios();

        scenarioListener.execute();
        Optional<ScenarioDto> scenarioDto = scenarioListener.getScenario();

        assertEquals(Optional.empty(), scenarioDto);
    }

    @Test
    void testGetScenarioIfThereAreTwoScenarios() {
        List<ScenarioDto> scenariosDtos = List.of(scenarioDtoMock, scenarioDtoMock);

        doReturn(scenariosDtos).when(scenarioSourceMock).getScenarios();

        scenarioListener.execute();

        for (ScenarioDto scenarioDto : scenariosDtos) {
            assertEquals(scenarioDto, scenarioListener.getScenario().get());
        }
    }
}
