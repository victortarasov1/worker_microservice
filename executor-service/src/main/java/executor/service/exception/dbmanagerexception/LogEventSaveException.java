package executor.service.exception.dbmanagerexception;

public class LogEventSaveException extends RuntimeException {
    public LogEventSaveException(Throwable cause) {
        super("Failed to save log event to the database.", cause);
    }
}
