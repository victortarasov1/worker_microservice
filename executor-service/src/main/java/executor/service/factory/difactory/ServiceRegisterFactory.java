package executor.service.factory.difactory;

import executor.service.exception.FoundSecondImplementationException;
import executor.service.exception.NoImplementationsFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class ServiceRegisterFactory implements DependencyInjectionFactory {
    private static final Map<Class<?>, Function<DependencyInjectionFactory, ?>> serviceMap = new HashMap<>();

    @Override
    public <T> T createInstance(Class<T> interfaceClass) {
        if (!serviceMap.containsKey(interfaceClass)) throw new NoImplementationsFoundException(interfaceClass);
        return interfaceClass.cast(serviceMap.get(interfaceClass).apply(this));
    }

    /**
     * Registers an interface class with its creator function.
     *
     * @param interfaceClass the interface class to register
     * @param creator        the function that creates an instance of the interface class
     * @throws FoundSecondImplementationException if a second implementation is found for the specified interface
     */
    public static void register(Class<?> interfaceClass, Function<DependencyInjectionFactory, ?> creator) {
        if (serviceMap.containsKey(interfaceClass)) throw new FoundSecondImplementationException(interfaceClass);
        serviceMap.put(interfaceClass, creator);
    }
}
