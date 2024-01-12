package executor.service.execution.exception.step.click;

import executor.service.execution.exception.step.StepExecutionException;

public class ClickIdException extends StepExecutionException {
    public ClickIdException(Throwable ex) {
        super("Failed to perform 'ClickId' step.", ex);
    }
}
