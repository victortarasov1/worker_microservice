package executor.service.factory.difactory.register;

import executor.service.exception.register.InstanceRegistrarException;

/**
 * Interface for registering instances of classes.
 */
public interface InstanceRegistrar {
    /**
     * Registers a class for creating its instance.
     *
     * @param clazz the class to be registered
     * @throws InstanceRegistrarException if an error occurs during the registration process
     */
    void register(Class<?> clazz);

    /**
     * Sets the next instance register in the chain.
     *
     * @param nextRegister the next instance register to be set
     */
    void setNextRegister(InstanceRegistrar nextRegister);
}
