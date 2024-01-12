package executor.service.execution.exception.step;

public class ClickClassNameException extends StepExecutionException {
    public ClickClassNameException(Throwable ex) {
        super("Failed to perform 'ClickClassName' step.", ex);
    }
}
