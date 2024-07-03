package executor.service.exception.step.click;

import executor.service.exception.step.StepExecutionException;

public class ClickPartialLinkTextException extends StepExecutionException {
    public ClickPartialLinkTextException(Throwable ex) {
        super("Failed to perform 'ClickPartialLinkText' step.", ex);
    }
}
