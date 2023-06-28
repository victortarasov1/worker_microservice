package executor.service.factory.difactory.register;

import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.exception.register.ConfigurationRegistrationException;
import executor.service.exception.register.MethodInvocationException;
import executor.service.factory.difactory.DependencyInjectionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

public class ConfigRegistrar implements InstanceRegistrar {
    private InstanceRegistrar nextRegister;

    @Override
    public void register(Class<?> clazz) {
        if(clazz.getDeclaredAnnotation(Config.class) != null) registerConfig(clazz);
        else if(nextRegister != null) nextRegister.register(clazz);
    }

    @Override
    public void setNextRegister(InstanceRegistrar nextRegister) {
        this.nextRegister = nextRegister;
    }

    private void registerConfig(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Arrays.stream(clazz.getMethods()).filter(v -> v.isAnnotationPresent(Bean.class))
                    .forEach(v -> ServiceCreatorRegistry.register(v.getReturnType(), getInstance(instance, v)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ConfigurationRegistrationException(clazz, e);
        }
    }

    private Function<DependencyInjectionFactory, Object> getInstance(Object instance, Method method) {
        return (factory) -> {
            try {
                return method.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                throw new MethodInvocationException(instance.getClass(), e);
            }
        };
    }
}
