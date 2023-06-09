package executor.service.exception;

public class SleepException extends StepExecutionException {
    public SleepException(String message) {
        super("Failed to perform 'Sleep' step. Reason: " + message);
    }
}
