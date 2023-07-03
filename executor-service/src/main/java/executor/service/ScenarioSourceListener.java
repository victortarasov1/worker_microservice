package executor.service;

import executor.service.model.ScenarioDto;

import java.util.Optional;

public interface ScenarioSourceListener {
    void execute();
    Optional<ScenarioDto> getScenario();
}
