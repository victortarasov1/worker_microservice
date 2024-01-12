package executor.service.execution.exception.step;

public class ClickIdException extends StepExecutionException {
    public ClickIdException(Throwable ex) {
        super("Failed to perform 'ClickId' step.", ex);
    }
}
