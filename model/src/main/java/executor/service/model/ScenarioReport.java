package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioReport {
    private Scenario scenario;
    private Set<StepReport> stepReports;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
    private String webDriverInfo;
    public UUID getUUID() {
        return scenario.getUuid();
    }
}
