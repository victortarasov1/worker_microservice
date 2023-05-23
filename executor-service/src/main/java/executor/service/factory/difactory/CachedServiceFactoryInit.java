package executor.service.factory.difactory;

import executor.service.factory.difactory.register.ComponentRegistrar;
import executor.service.factory.difactory.register.ConfigRegistrar;
import executor.service.factory.difactory.register.InstanceRegistrar;
import executor.service.factory.difactory.scanner.ComponentScannerImpl;

class CachedServiceFactoryInit implements ServiceFactoryInitializer {

    private final DependencyInjectionFactory factory;

    public CachedServiceFactoryInit() {
        String packageName = "executor.service";
        InstanceRegistrar register = new ComponentRegistrar(packageName);
        register.setNextRegister(new ConfigRegistrar());
        new ComponentScannerImpl(packageName).getComponents().forEach(register::register);
        this.factory = new CachedServiceFactory(new ServiceCreatorRegistry());
    }

    @Override
    public DependencyInjectionFactory getServiceFactory() {
        return factory;
    }

}
