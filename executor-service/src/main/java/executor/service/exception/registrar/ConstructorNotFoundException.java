package executor.service.exception.registrar;

public class ConstructorNotFoundException extends InstanceRegistrarException {

    public ConstructorNotFoundException(Class<?> clazz) {
        super("constructor for " + clazz.getName() + " not found!");
    }

}
