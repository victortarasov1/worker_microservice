package executor.service.factory.difactory;

import executor.service.exception.UnregisteredClassException;

public interface RegisterFactory {
    /**
     * Creates an instance of a class.
     *
     * @param type the class for which to create an instance
     * @param <T>  the type of the class
     * @param factory the dependency injection factory used to create the instance
     * @throws UnregisteredClassException if the specified class is not registered in the factory
     * @return an instance of the class
     */
    <T> T createInstance(Class<T> type, DependencyInjectionFactory factory);
}
