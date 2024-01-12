package executor.service.execution.exception.step.click;


import executor.service.execution.exception.step.StepExecutionException;

public class ClickXPathException extends StepExecutionException {
    public ClickXPathException(Throwable cause) {
        super("Failed to perform 'clickXpath' step.", cause);
    }
}
