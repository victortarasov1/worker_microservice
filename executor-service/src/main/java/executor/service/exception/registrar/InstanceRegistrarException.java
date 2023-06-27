package executor.service.exception.registrar;

public class InstanceRegistrarException extends RuntimeException {
    public InstanceRegistrarException(String message) {
        super(message);
    }

    public InstanceRegistrarException(String message, Throwable cause) {
        super(message, cause);
    }
}
