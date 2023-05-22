package executor.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code @Config} annotation is used to allow the dependency injection factory to create objects.
 * <p>
 * The class annotated with {@code @Config} should have a public default constructor.
 * This allows the dependency injection factory to instantiate the configuration and use it for creating instances.
 * If a public default constructor is not found, a {@code ConfigurationRegistrationException} will be thrown.
 * <p>
 * To specify what objects should be injected by the factory, and how they should be configured,
 * you can use the {@code @Bean} annotation by annotating a method with {@code @Bean}
 * inside the class annotated with {@code @Config}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Config {
}
