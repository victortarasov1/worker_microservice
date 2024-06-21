package executor.service.aop.logger.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {
    private final Logger logger;
    private static final String POINT = """
                @within(executor.service.aop.logger.annotation.Logged)||
                @annotation(executor.service.aop.logger.annotation.Logged)
            """;

    @Before(POINT)
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        logger.info(LogMessage.EXECUTING_METHOD.getMessage(), methodName, className, args);
    }

    @AfterReturning(pointcut = POINT, returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        if (result != null) logger.info(LogMessage.METHOD_RETURN_VALUE.getMessage(), methodName, className, result);
        else logger.info(LogMessage.METHOD_EXECUTION_COMPLETED.getMessage(), methodName, className);
    }
}
