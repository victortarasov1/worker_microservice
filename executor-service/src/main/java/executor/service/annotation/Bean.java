package executor.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code @Bean} annotation is used to annotate methods in a class annotated with {@code @Config},
 * which are intended to return instances of interfaces for the dependency injection factory.
 * <p>
 * The annotated method should be public, have no parameters, and return an interface rather than its implementation.
 * If these conditions are not met, a {@code MethodInvocationException} will be thrown.
 * <p>
 * For example:
 * {@code @Bean}
 * {@code public SomeInterface someBean() {
 * return new SomeInterfaceImpl(dependencies);
 * }}
 * <p>
 * In this example, the {@code someBean()} method returns an instance of the {@code SomeInterface} interface.
 * This method will be called by the dependency injection factory to provide the implementation for dependency injection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {
}
