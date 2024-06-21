package executor.service.exception.step.click;

import executor.service.exception.step.StepExecutionException;

public class ClickLinkTextException extends StepExecutionException {
    public ClickLinkTextException(Throwable ex) {
        super("Failed to perform 'ClickLinkText' step.", ex);
    }
}
