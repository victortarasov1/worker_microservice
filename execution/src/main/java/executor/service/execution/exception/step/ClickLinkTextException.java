package executor.service.execution.exception.step;

public class ClickLinkTextException extends StepExecutionException {
    public ClickLinkTextException(Throwable ex) {
        super("Failed to perform 'ClickLinkText' step.", ex);
    }
}
