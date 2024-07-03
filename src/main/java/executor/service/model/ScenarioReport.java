package executor.service.model;

import java.time.LocalDateTime;
import java.util.List;


public record ScenarioReport (
        String scenarioId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String errorMessage,
        String webDriverInfo,
        List<StepReport> stepReports,
        String name,
        String site
) {
}
