package executor.service.exception.step.click;

import executor.service.exception.step.StepExecutionException;

public class ClickClassNameException extends StepExecutionException {
    public ClickClassNameException(Throwable ex) {
        super("Failed to perform 'ClickClassName' step.", ex);
    }
}
