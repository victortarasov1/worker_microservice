package executor.service.report.aspect;

import executor.service.collection.map.step.StepReportMapHandler;
import executor.service.collection.queue.scenario.ScenarioReportQueueHandler;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@RequiredArgsConstructor
@Order(1)
public class ScenarioReportAspect {
    private final ScenarioReportQueueHandler scenarios;
    private final StepReportMapHandler steps;


    @Around("execution(* executor.service.execution.scenario.ScenarioExecutor.execute(..))")
    public void makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        var report = new ScenarioReport();
        var scenario = (Scenario) joinPoint.getArgs()[0];
        var webDriverInfo = joinPoint.getArgs()[1].toString();
        report.setScenario(scenario);
        report.setWebDriverInfo(webDriverInfo);
        report.setStartTime(LocalDateTime.now());
        proceed(joinPoint, report);
    }

    private void proceed(ProceedingJoinPoint joinPoint, ScenarioReport report) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (Exception ex) {
            report.setErrorMessage(ex.getMessage());
            throw ex;
        } finally {
            report.setEndTime(LocalDateTime.now());
            report.setStepReports(steps.remove(report.getUUID()));
            scenarios.add(report);
        }
    }
}
