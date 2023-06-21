package executor.service.exception.connectionexception;

public class ConnectionFailedException extends RuntimeException {
    public ConnectionFailedException(Throwable cause) {
        super("Failed to connect to the database.", cause);
    }
}
