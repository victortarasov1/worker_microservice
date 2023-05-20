package executor.service.factory.difactory;

public class CashedServiceFactoryProvider {
    private static ServiceFactoryInitializer initializer;

    private CashedServiceFactoryProvider() {

    }

    public static DependencyInjectionFactory getFactory() {
        if (initializer == null) initializer = new CashedServiceFactoryInit();
        return initializer.getServiceFactory();
    }

}
