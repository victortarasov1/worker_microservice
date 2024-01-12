package executor.service.execution.exception.step.click;

import executor.service.execution.exception.step.StepExecutionException;

public class ClickNameException extends StepExecutionException {
    public ClickNameException(Throwable ex) {
        super("Failed to perform 'ClickName' step.", ex);
    }
}
