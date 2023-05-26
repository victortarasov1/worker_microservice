package executor.service.exception;

public class ConfigurationRegistrationException extends RuntimeException {

    public ConfigurationRegistrationException(String message, Class<?> clazz) {
        super("Exception on registering instances from configuration class: " + clazz.getName() + ". Reason: " + message);
    }
}
