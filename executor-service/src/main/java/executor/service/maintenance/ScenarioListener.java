package executor.service.maintenance;

import executor.service.model.ScenarioDto;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class ScenarioListener implements ScenarioSourceListener {
    private final ScenarioSource scenarioSource;
    private Deque<ScenarioDto> scenarios;

    public ScenarioListener(ScenarioSource scenarioSource) {
        this.scenarioSource = scenarioSource;
    }

    @Override
    public void execute() {
        scenarios = new ConcurrentLinkedDeque<>(scenarioSource.getScenarios());
    }

    @Override
    public Optional<ScenarioDto> getScenario() {
        return Optional.ofNullable(scenarios.pollFirst());
    }
}
