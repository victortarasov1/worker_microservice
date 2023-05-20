package executor.service.expetion;

public class ConstructorNotFoundException extends RuntimeException {

    public ConstructorNotFoundException(Class<?> clazz) {
        super("constructor for " + clazz.getName() + " not found!");
    }

}
