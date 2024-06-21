package executor.service.logback.exception.logstorage;

public class DisconnectionFailedException extends RuntimeException {
    public DisconnectionFailedException(Throwable cause) {
        super("Failed to disconnect from the database.", cause);
    }
}
