package executor.service.exception.logstorage;

public class ConnectionFailedException extends RuntimeException {
    public ConnectionFailedException(Throwable cause) {
        super("Failed to connect to the database.", cause);
    }
}
