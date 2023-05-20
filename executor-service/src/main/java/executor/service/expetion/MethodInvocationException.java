package executor.service.expetion;

public class MethodInvocationException extends RuntimeException {

    public MethodInvocationException(String message, Class<?> clazz) {
        super("Exception on invoking method in class: " + clazz.getName() + ". Reason: " + message);
    }

}
