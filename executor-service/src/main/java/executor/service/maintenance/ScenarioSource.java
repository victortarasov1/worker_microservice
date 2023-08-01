package executor.service.maintenance;

import executor.service.model.ScenarioDto;

import java.util.List;

public interface ScenarioSource {
    List<ScenarioDto> getScenarios();
}
