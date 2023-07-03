package executor.service.factory.difactory.register;

import executor.service.exception.register.DuplicateRegistrationException;
import executor.service.exception.register.UnregisteredClassException;
import executor.service.factory.difactory.DependencyInjectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class ServiceCreatorRegistry implements InstanceCreatorRegistry {
    private static final Map<Class<?>, Function<DependencyInjectionFactory, ?>> serviceMap = new HashMap<>();

    @Override
    public Function<DependencyInjectionFactory, ?> getCreatorFunction(Class<?> type) {
        if (!serviceMap.containsKey(type)) throw new UnregisteredClassException(type);
        return serviceMap.get(type);
    }

    /**
     * Registers a class with its creator function.
     *
     * @param type    the class to register
     * @param creator the function that creates an instance of the class
     * @throws DuplicateRegistrationException if a duplicate registration is attempted for the specified class
     */
    public static void register(Class<?> type, Function<DependencyInjectionFactory, ?> creator) {
        if (serviceMap.containsKey(type)) throw new DuplicateRegistrationException(type);
        serviceMap.put(type, creator);
    }

}
