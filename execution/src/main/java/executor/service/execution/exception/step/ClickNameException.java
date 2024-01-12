package executor.service.execution.exception.step;

public class ClickNameException extends StepExecutionException {
    public ClickNameException(Throwable ex) {
        super("Failed to perform 'ClickName' step.", ex);
    }
}
