package executor.service.factory.difactory;

/**
 * serves as a facade for the DI factory
 */
public interface ServiceFactoryInitializer {

    /**
     * Returns the DependencyInjectionFactory instance.
     *
     * @return The DependencyInjectionFactory instance.
     */
    DependencyInjectionFactory getServiceFactory();
}
