package executor.service.exception;

public class StepExecutionException extends RuntimeException {
    public StepExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
