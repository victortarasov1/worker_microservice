package executor.service.execution.exception.step.click;

import executor.service.execution.exception.step.StepExecutionException;

public class ClickTagNameException extends StepExecutionException {
    public ClickTagNameException(Throwable ex) {
        super("Failed to perform 'ClickTagName' step.", ex);
    }
}
