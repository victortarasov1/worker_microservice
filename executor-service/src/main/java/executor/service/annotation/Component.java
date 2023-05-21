package executor.service.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code @Component} annotation is used to specify which implementation of an interface should be used for dependency injection.
 * It indicates which implementation of an interface should be selected and injected when resolving dependencies.
 * <p>
 * Only one of the implementations of your interface can be annotated as a component using the {@code @Component} annotation.
 * If multiple implementations of the interface are annotated, a {@code FoundSecondImplementationException} will be thrown.
 * <p>
 * The annotated implementation should have a public default constructor or a constructor with parameters that represent its dependencies.
 * This allows the dependency injection factory to instantiate the component and inject its dependencies correctly.
 * If a constructor is not found, a {@code ConstructorNotFoundException} will be thrown
 * <p>
 * The parameters for the constructor should be your other interfaces. However, if you need to specify primitive types, wrapper classes,
 * collections and so on as constructor parameters, you can consider using the {@code @Config} and {@code @Bean} annotations.
 * If these conditions are not met, an {@code InstanceCreationException} will be thrown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
}
