package executor.service.factory.difactory;

import executor.service.factory.difactory.register.ComponentRegister;
import executor.service.factory.difactory.register.ConfigRegister;
import executor.service.factory.difactory.register.InstanceRegister;
import executor.service.factory.difactory.scanner.ComponentScannerImpl;

class CashedServiceFactoryInit implements ServiceFactoryInitializer {

    private final DependencyInjectionFactory factory;

    public CashedServiceFactoryInit() {
        String packageName = "executor.service";
        InstanceRegister register = new ComponentRegister(packageName);
        register.setNextRegister(new ConfigRegister());
        new ComponentScannerImpl(packageName).getComponents().forEach(register::register);
        this.factory = new CashedServiceFactory(new ServiceRegisterFactory());
    }

    @Override
    public DependencyInjectionFactory getServiceFactory() {
        return factory;
    }

}
