package executor.service.execution.exception.step;

public class ClickTagNameException extends StepExecutionException {
    public ClickTagNameException(Throwable ex) {
        super("Failed to perform 'ClickTagName' step.", ex);
    }
}
