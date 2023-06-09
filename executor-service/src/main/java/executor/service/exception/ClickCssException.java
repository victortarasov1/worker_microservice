package executor.service.exception;

public class ClickCssException extends StepExecutionException {
    public ClickCssException(String message) {
        super("Failed to perform 'clickCss' step. Reason: " + message);
    }
}
