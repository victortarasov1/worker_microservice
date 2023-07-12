package executor.service.exception.scenario.step;

import executor.service.exception.scenario.ScenarioExecutionException;

public class StepExecutionException extends ScenarioExecutionException {
    public StepExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
    public StepExecutionException(String message) {
        super(message);
    }
}
