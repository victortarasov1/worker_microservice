package executor.service.exception.registrar;

public class ConfigurationRegistrationException extends InstanceRegistrarException {

    public ConfigurationRegistrationException(Class<?> clazz, Throwable cause) {
        super("Exception on registering instances from configuration class: " + clazz.getName(), cause);
    }
}
