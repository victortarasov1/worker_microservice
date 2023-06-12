package executor.service.exception.stepexception;


public class ClickXPathException extends StepExecutionException {
    public ClickXPathException(Throwable cause) {
        super("Failed to perform 'clickXpath' step.", cause);
    }
}
