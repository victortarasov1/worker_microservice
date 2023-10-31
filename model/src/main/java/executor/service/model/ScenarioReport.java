package executor.service.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


public record ScenarioReport(Scenario scenario, Set<StepReport> stepReports, LocalDateTime startTime,
                             LocalDateTime endTime, String errorMessage, String webDriverInfo) {
    public UUID getUUID() {
        return scenario.uuid();
    }
}
