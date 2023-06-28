package executor.service.logger;

import org.slf4j.Logger;
import java.lang.reflect.Proxy;

public class LoggingProxyProvider {
    private LoggingProxyProvider() {

    }

    public static <T> T createProxy(T target, Class<T> tInterface, Logger logger) {
        var handler = new LoggingInvocationHandler<>(target, logger);
        return tInterface.cast(Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler)
        );
    }
}
