package executor.service.execution.exception.step.click;


import executor.service.execution.exception.step.StepExecutionException;

public class ClickCssException extends StepExecutionException {
    public ClickCssException(Throwable cause) {
        super("Failed to perform 'clickCss' step.", cause);
    }
}
