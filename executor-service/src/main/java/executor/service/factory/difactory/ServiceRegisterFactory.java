package executor.service.factory.difactory;

import executor.service.exception.DuplicateRegistrationException;
import executor.service.exception.UnregisteredClassException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class ServiceRegisterFactory implements RegisterFactory {
    private static final Map<Class<?>, Function<DependencyInjectionFactory, ?>> serviceMap = new HashMap<>();

    @Override
    public <T> T createInstance(Class<T> type, DependencyInjectionFactory factory) {
        if (!serviceMap.containsKey(type)) throw new UnregisteredClassException(type);
        return type.cast(serviceMap.get(type).apply(factory));
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
