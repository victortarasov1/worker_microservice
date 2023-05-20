package executor.service.expetion;

public class InstanceCreationException extends RuntimeException{

    public InstanceCreationException(String message, Class<?> clazz) {
        super("Exception on creating an instance of class: " + clazz.getName() + ". Reason: " + message);
    }

}
