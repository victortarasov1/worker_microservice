package executor.service.report.aspect;

import executor.service.model.Scenario;
import executor.service.redis.queue.producer.QueueProducer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class ReportProducingAspect {
    private final QueueProducer producer;

    @After("execution(* executor.service.execution.scenario.ScenarioExecutor.execute(..))")
    public void produce(JoinPoint joinPoint) {
        Scenario scenario = (Scenario) joinPoint.getArgs()[0];
        producer.add(scenario);
    }
}
