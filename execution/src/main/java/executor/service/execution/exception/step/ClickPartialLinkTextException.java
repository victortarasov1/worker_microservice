package executor.service.execution.exception.step;

public class ClickPartialLinkTextException extends StepExecutionException {
    public ClickPartialLinkTextException(Throwable ex) {
        super("Failed to perform 'ClickPartialLinkText' step.", ex);
    }
}
