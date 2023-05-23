package executor.service.factory.difactory;


import java.util.HashMap;
import java.util.Map;

class CachedServiceFactory implements DependencyInjectionFactory {
    private final Map<Class<?>, Object> cachedInstances;
    private final InstanceCreatorRegistry registry;

    public CachedServiceFactory(InstanceCreatorRegistry registry) {
        this.registry = registry;
        cachedInstances = new HashMap<>();
    }

    @Override
    public <T> T createInstance(Class<T> type) {
        return cachedInstances.containsKey(type) ?
                type.cast(cachedInstances.get(type))
                : cacheInstance(type);
    }

    private <T> T cacheInstance(Class<T> type) {
        T instance = type.cast(registry.getCreatorFunction(type).apply(this));
        cachedInstances.put(type, instance);
        return instance;
    }
}