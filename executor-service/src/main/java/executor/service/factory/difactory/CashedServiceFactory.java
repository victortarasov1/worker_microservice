package executor.service.factory.difactory;


import java.util.HashMap;
import java.util.Map;

class CashedServiceFactory implements DependencyInjectionFactory {
    private final Map<Class<?>, Object> cashedInstances;
    private final DependencyInjectionFactory serviceFactory;

    public CashedServiceFactory(DependencyInjectionFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        cashedInstances = new HashMap<>();
    }

    @Override
    public <T> T createInstance(Class<T> interfaceClass) {
        if (cashedInstances.containsKey(interfaceClass))
            return interfaceClass.cast(cashedInstances.get(interfaceClass));
        T instance = serviceFactory.createInstance(interfaceClass);
        cashedInstances.put(interfaceClass, instance);
        return instance;
    }
}