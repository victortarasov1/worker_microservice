package executor.service.execution.exception.step.click;

import executor.service.execution.exception.step.StepExecutionException;

public class ClickPartialLinkTextException extends StepExecutionException {
    public ClickPartialLinkTextException(Throwable ex) {
        super("Failed to perform 'ClickPartialLinkText' step.", ex);
    }
}
