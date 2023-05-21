package executor.service.exception;

public class NoImplementationsFoundException extends RuntimeException{

    public NoImplementationsFoundException(Class<?> implementationClazz) {
        super("No implementations found for " + implementationClazz.getName());
    }

}
