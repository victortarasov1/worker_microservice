package executor.service.execution.exception.step.click;

import executor.service.execution.exception.step.StepExecutionException;

public class ClickClassNameException extends StepExecutionException {
    public ClickClassNameException(Throwable ex) {
        super("Failed to perform 'ClickClassName' step.", ex);
    }
}
