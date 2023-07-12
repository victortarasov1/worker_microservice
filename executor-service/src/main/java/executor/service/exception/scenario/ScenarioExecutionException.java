package executor.service.exception.scenario;

public class ScenarioExecutionException extends RuntimeException {
    public ScenarioExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
    public ScenarioExecutionException(String message) {
        super(message);
    }
}
