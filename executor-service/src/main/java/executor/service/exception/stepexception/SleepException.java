package executor.service.exception.stepexception;


public class SleepException extends StepExecutionException {
    public SleepException(Throwable cause) {
        super("Failed to perform 'Sleep' step.", cause);
    }
}
