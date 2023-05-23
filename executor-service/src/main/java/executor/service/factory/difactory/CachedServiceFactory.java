package executor.service.factory.difactory;


import java.util.HashMap;
import java.util.Map;

class CachedServiceFactory implements DependencyInjectionFactory {
    private final Map<Class<?>, Object> cachedInstances;
    private final RegisterFactory serviceFactory;

    public CachedServiceFactory(RegisterFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        cachedInstances = new HashMap<>();
    }

    @Override
    public <T> T createInstance(Class<T> type) {
        if (cachedInstances.containsKey(type))
            return type.cast(cachedInstances.get(type));
        T instance = serviceFactory.createInstance(type, this);
        cachedInstances.put(type, instance);
        return instance;
    }
}