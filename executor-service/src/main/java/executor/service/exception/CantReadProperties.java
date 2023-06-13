package executor.service.exception;

public class CantReadProperties extends RuntimeException{
    public CantReadProperties(String message) {
        super("cant read properties. reason: " + message);
    }
}
