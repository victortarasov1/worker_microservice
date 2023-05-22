package executor.service.factory.difactory;

public class CachedServiceFactoryProvider {
    private static ServiceFactoryInitializer initializer;

    private CachedServiceFactoryProvider() {

    }

    public static DependencyInjectionFactory getFactory() {
        if (initializer == null) initializer = new CachedServiceFactoryInit();
        return initializer.getServiceFactory();
    }

}
