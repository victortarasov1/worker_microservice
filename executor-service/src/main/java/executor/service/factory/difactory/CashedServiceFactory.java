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
    public <T> T createInstance(Class<T> type) {
        if (cashedInstances.containsKey(type))
            return type.cast(cashedInstances.get(type));
        T instance = serviceFactory.createInstance(type);
        cashedInstances.put(type, instance);
        return instance;
    }
}