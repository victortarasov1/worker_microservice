package executor.service.execution.exception.step;

import executor.service.execution.exception.ScenarioExecutionException;

public class StepExecutionException extends ScenarioExecutionException {
    public StepExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
    public StepExecutionException(String message) {
        super(message);
    }
}
