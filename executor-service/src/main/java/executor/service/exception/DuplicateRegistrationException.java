package executor.service.exception;

public class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(Class<?> clazz){
        super("Found duplicate registration for " + clazz.getName());
    }
}
