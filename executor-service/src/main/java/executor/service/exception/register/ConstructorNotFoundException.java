package executor.service.exception.register;

public class ConstructorNotFoundException extends InstanceRegistrarException {

    public ConstructorNotFoundException(Class<?> clazz) {
        super("constructor for " + clazz.getName() + " not found!");
    }

}
