package executor.service.logger;

import executor.service.exception.scenario.ScenarioExecutionException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class ScenarioExceptionHandlingAspect {
    private final Logger logger;

    @Around("execution(* executor.service.execution.scenario.ScenarioExecutor+.*(..))")
    public void handle(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (ScenarioExecutionException ex) {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.error(LogMessage.INVOCATION_TARGET_EXCEPTION.getMessage(), methodName, className, ex);
        }
    }
}
