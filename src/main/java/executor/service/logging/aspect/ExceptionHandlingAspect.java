package executor.service.aop.logger.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Order(1)
public class ExceptionHandlingAspect {
    private final Logger logger;

    @Around("@within(executor.service.aop.logger.annotation.HandleException)")
    public void handle(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (Exception ex) {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.error(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage(), methodName, className, ex);
        }
    }
}
