package executor.service.factory.difactory.register;

import executor.service.factory.difactory.DependencyInjectionFactory;

import java.util.function.Function;

/**
 * The interface for a registry of instance creator functions.
 * It provides a method to retrieve the creator function for a given class type.
 */
public interface InstanceCreatorRegistry {
    /**
     * Retrieves the creator function for the specified class type.
     *
     * @param type the class type for which to retrieve the creator function
     * @return the creator function that creates an instance of the class
     * @throws executor.service.exception.UnregisteredClassException if the specified class is not registered in the registry
     */
    Function<DependencyInjectionFactory, ?> getCreatorFunction(Class<?> type);
}