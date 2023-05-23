package executor.service.factory.difactory.register;

import executor.service.annotation.Component;
import executor.service.exception.ConstructorNotFoundException;
import executor.service.exception.InstanceCreationException;
import executor.service.factory.difactory.DependencyInjectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComponentRegistrar implements InstanceRegistrar {
    private InstanceRegistrar nextRegister;
    private final String packageName;

    public ComponentRegistrar(String packageName) {
        this.packageName = packageName;
    }
    @Override
    public void register(Class<?> clazz) {
        if (clazz.getDeclaredAnnotation(Component.class) != null) registerComponent(clazz);
        else if (nextRegister != null) nextRegister.register(clazz);
    }

    @Override
    public void setNextRegister(InstanceRegistrar nextRegister) {
        this.nextRegister = nextRegister;
    }
    private void registerComponent(Class<?> clazz) {
        Predicate<Class<?>> isDesiredInterface = interfaceClazz
                -> interfaceClazz.getPackage().getName().startsWith(packageName);
        Arrays.stream(clazz.getInterfaces()).filter(isDesiredInterface)
                .forEach(interfaceClazz -> ServiceCreatorRegistry.register(interfaceClazz, getInstance(clazz)));
    }

    private Function<DependencyInjectionFactory, Object> getInstance(Class<?> clazz) {
        return (serviceFactory) -> {
            try {
                Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                        .findFirst().orElseThrow(() -> new ConstructorNotFoundException(clazz));
                Object[] objects = Arrays.stream(constructor.getParameters()).map(Parameter::getType).map(serviceFactory::createInstance).toArray();
                return constructor.newInstance(objects);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
                throw new InstanceCreationException(ex.getMessage(), clazz);
            }
        };
    }
}
