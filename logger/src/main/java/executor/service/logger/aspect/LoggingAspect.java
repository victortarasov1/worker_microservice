package executor.service.logger.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {
    private final Logger logger;

    @Before("@within(executor.service.logger.annotation.Logged)")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        logger.info(LogMessage.EXECUTING_METHOD.getMessage(), methodName, className, args);
    }

    @After("@within(executor.service.logger.annotation.Logged)")
    public void logAfter(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info(LogMessage.METHOD_EXECUTION_COMPLETED.getMessage(), methodName, className);
    }
}
