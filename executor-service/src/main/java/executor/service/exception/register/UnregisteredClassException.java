package executor.service.exception.register;

public class UnregisteredClassException extends RuntimeException{

    public UnregisteredClassException(Class<?> clazz) {
        super("Class isn`t registered in the DI factory: " + clazz.getName());
    }

}
