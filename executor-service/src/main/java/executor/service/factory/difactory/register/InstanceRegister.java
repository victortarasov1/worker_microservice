package executor.service.factory.difactory.register;

/**
 * Interface for registering instances of classes.
 */
public interface InstanceRegister {
    /**
     * Registers a class for creating its instance.
     *
     * @param clazz the class to be registered
     */
    void register(Class<?> clazz);

    /**
     * Sets the next instance register in the chain.
     *
     * @param nextRegister the next instance register to be set
     */
    void setNextRegister(InstanceRegister nextRegister);
}
