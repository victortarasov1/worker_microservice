package executor.service.factory.difactory.register;

import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.exception.ConfigurationRegistrationException;
import executor.service.exception.MethodInvocationException;
import executor.service.factory.difactory.DependencyInjectionFactory;
import executor.service.factory.difactory.ServiceRegisterFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

public class ConfigRegister implements InstanceRegister {
    private InstanceRegister nextRegister;

    @Override
    public void register(Class<?> clazz) {
        if(clazz.getDeclaredAnnotation(Config.class) != null) registerConfig(clazz);
        else if(nextRegister != null) nextRegister.register(clazz);
    }

    @Override
    public void setNextRegister(InstanceRegister nextRegister) {
        this.nextRegister = nextRegister;
    }

    private void registerConfig(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Arrays.stream(clazz.getMethods()).filter(v -> v.isAnnotationPresent(Bean.class))
                    .forEach(v -> ServiceRegisterFactory.register(v.getReturnType(), getInstance(instance, v)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ConfigurationRegistrationException(e.getMessage(), clazz);
        }
    }

    private Function<DependencyInjectionFactory, Object> getInstance(Object instance, Method method) {
        return (factory) -> {
            try {
                return method.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                throw new MethodInvocationException(e.getMessage(), instance.getClass());
            }
        };
    }
}
