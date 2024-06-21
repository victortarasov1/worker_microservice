package executor.service.aop.logger.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that methods or classes annotated with this
 * annotation will be logged.
 * Logging includes the start of method execution, which logs the
 * method name, class name, and method arguments,
 * and the end of method execution, which logs the method name and
 * class name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Logged {
}
