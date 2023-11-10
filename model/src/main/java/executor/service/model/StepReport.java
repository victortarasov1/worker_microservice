package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalTime;

@RedisHash("step_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepReport {
    @Id
    private String id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String errorMessage;
}
