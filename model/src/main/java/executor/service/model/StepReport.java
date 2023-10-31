package executor.service.model;

import java.time.LocalTime;
import java.util.UUID;

public record StepReport(Step step, LocalTime startTime, LocalTime endTime, String errorMessage) {
    public UUID getScenarioUUID() {
        return step.scenarioUUID();
    }
}
