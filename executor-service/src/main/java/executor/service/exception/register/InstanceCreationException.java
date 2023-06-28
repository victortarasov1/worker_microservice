package executor.service.exception.register;

public class InstanceCreationException extends InstanceRegistrarException {

    public InstanceCreationException(Class<?> clazz, Throwable cause) {
        super("Exception on creating an instance of class: " + clazz.getName(), cause);
    }

}
