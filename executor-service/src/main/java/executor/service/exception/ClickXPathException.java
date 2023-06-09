package executor.service.exception;

public class ClickXPathException extends StepExecutionException {
    public ClickXPathException(String message) {
        super("Failed to perform 'clickXpath' step. Reason: " + message);
    }
}
