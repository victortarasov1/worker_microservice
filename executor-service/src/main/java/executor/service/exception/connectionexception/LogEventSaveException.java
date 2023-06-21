package executor.service.exception.connectionexception;

public class LogEventSaveException extends RuntimeException {
    public LogEventSaveException(Throwable cause) {
        super("Failed to save log event to the database.", cause);
    }
}
