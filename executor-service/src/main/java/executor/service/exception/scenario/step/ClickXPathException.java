package executor.service.exception.scenario.step;


public class ClickXPathException extends StepExecutionException {
    public ClickXPathException(Throwable cause) {
        super("Failed to perform 'clickXpath' step.", cause);
    }
}
