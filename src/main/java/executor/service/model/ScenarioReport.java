package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioReport {
    private String scenarioId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
    private String webDriverInfo;
}
