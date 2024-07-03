package executor.service.exception.step.click;


import executor.service.exception.step.StepExecutionException;

public class ClickCssException extends StepExecutionException {
    public ClickCssException(Throwable cause) {
        super("Failed to perform 'clickCss' step.", cause);
    }
}
