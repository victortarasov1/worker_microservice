package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    private StepReport report;
    private String action;
    private String value;

    public Step(String action, String value) {
        this.action = action;
        this.value = value;
    }
}