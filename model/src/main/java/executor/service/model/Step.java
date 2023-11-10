package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("steps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    @Id
    private String id;
    @Reference
    private StepReport report;
    private String action;
    private String value;

    public Step(String action, String value) {
        this.action = action;
        this.value = value;
    }
}