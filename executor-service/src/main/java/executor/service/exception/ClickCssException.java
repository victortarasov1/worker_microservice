package executor.service.exception;

public class ClickCssException extends StepExecutionException {
    public ClickCssException(Throwable cause) {
        super("Failed to perform 'clickCss' step.", cause);
    }
}
