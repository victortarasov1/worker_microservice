package executor.service.exception.scenario.step;

public class UnknownStepException extends StepExecutionException{
    public UnknownStepException(String message, Throwable cause) {
        super("Step with name: " + message +  " doesn`t exist!", cause);
    }
    public UnknownStepException(String message) {
        super(message);
    }
}
