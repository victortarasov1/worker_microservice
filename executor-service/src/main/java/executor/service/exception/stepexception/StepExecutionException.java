package executor.service.exception.stepexception;

public class StepExecutionException extends RuntimeException {
    public StepExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
