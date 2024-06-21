package executor.service.execution.exception.step.click;

import executor.service.execution.exception.step.StepExecutionException;

public class ClickLinkTextException extends StepExecutionException {
    public ClickLinkTextException(Throwable ex) {
        super("Failed to perform 'ClickLinkText' step.", ex);
    }
}
