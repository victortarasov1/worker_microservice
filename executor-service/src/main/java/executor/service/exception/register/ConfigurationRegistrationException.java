package executor.service.exception.register;

public class ConfigurationRegistrationException extends InstanceRegistrarException {

    public ConfigurationRegistrationException(Class<?> clazz, Throwable cause) {
        super("Exception on registering instances from configuration class: " + clazz.getName(), cause);
    }
}
