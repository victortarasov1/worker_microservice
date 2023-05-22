package executor.service.factory.difactory;

import executor.service.exception.UnregisteredClassException;

/**
 * A factory interface for creating instances of classes based on their interface.
 */
public interface DependencyInjectionFactory {

    /**
     * Creates an instance of a class.
     *
     * @param type the class for which to create an instance
     * @param <T>  the type of the class
     * @throws UnregisteredClassException if the specified class is not registered in the factory
     * @return an instance of the class
     */
    <T> T createInstance(Class<T> type);

}
