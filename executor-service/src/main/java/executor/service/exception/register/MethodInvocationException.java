package executor.service.exception.register;

public class MethodInvocationException extends InstanceRegistrarException {

    public MethodInvocationException(Class<?> clazz, Throwable cause) {
        super("Exception on invoking method in class: " + clazz.getName(), cause);
    }

}
