package executor.service.factory.difactory;

/**
 * A factory interface for creating instances of classes based on their interface.
 */
public interface DependencyInjectionFactory {

    /**
     * Creates an instance of a class that implements the specified interface.
     *
     * @param interfaceClass the interface class for which to create an instance
     * @param <T>            the type of the interface
     * @throws executor.service.exception.NoImplementationsFoundException if no implementations is found
     * @return an instance of the class that implements the interface
     */
    <T> T createInstance(Class<T> interfaceClass);

}
