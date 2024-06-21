package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepReport {
    private LocalTime startTime;
    private LocalTime endTime;
    private String errorMessage;
}
