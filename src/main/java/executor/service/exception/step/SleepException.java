package executor.service.execution.exception.step;


import executor.service.execution.exception.step.StepExecutionException;

public class SleepException extends StepExecutionException {
    public SleepException(Throwable cause) {
        super("Failed to perform 'Sleep' step.", cause);
    }
}
