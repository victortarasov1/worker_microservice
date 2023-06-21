package executor.service.exception.dbmanagerexception;

public class StackTraceSaveException extends RuntimeException {
    public StackTraceSaveException(Throwable cause) {
        super("Failed to save exception stack trace to the database.", cause);
    }
}
