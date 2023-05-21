package executor.service.exception;

public class FoundSecondImplementationException extends RuntimeException {
    public FoundSecondImplementationException(Class<?> interfaceClazz){
        super("Found second implementation for " + interfaceClazz.getName());
    }
}
