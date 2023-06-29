package executor.service.logger;

import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class LoggingInvocationHandler<T> implements InvocationHandler {
    private final T target;
    private final Logger logger;

    LoggingInvocationHandler(T target, Logger logger) {
        this.target = target;
        this.logger = logger;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        logger.info(LogMessage.EXECUTING_METHOD.getMessage(), method.getName(), target.getClass().getSimpleName(), args);
        Object result = invokeMethod(method, args);
        logger.info(LogMessage.METHOD_EXECUTION_COMPLETED.getMessage(), target.getClass().getSimpleName(), result);
        return result;
    }

    private Object invokeMethod(Method method, Object[] args) throws Throwable {
        try{
            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            Throwable targetException = ex.getTargetException();
            logger.error(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage(), target.getClass().getSimpleName(), targetException);
            throw targetException;
        }
    }

}