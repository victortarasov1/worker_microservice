package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("scenario_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioReport {
    @Id
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
    private String webDriverInfo;
}
