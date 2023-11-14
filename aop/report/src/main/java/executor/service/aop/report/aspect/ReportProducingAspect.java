package executor.service.aop.report.aspect;

import executor.service.model.Scenario;
import executor.service.redis.queue.producer.QueueProducer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Order(2)
public class ReportProducingAspect {
    private final QueueProducer producer;

    @Around("execution(* executor.service.execution.scenario.ScenarioExecutor.execute(..))")
    public void produce(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
        } finally {
            Scenario scenario = (Scenario) joinPoint.getArgs()[0];
            producer.add(scenario);
        }
    }
}
