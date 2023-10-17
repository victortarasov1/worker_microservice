package executor.service.execution.exception;

public class SiteNotFoundException extends ScenarioExecutionException {
    public SiteNotFoundException(String url, Throwable cause) {
        super("Site by url: " + url + " is not found!", cause);
    }
}
