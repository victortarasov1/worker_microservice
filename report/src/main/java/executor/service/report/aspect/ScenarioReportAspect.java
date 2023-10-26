package executor.service.report.aspect;

import executor.service.collection.map.step.StepReportMapHandler;
import executor.service.collection.queue.scenario.ScenarioReportQueueHandler;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import lombok.RequiredArgsConstructor;
import lombok.val;
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

    @Around("@within(executor.service.report.annotation.ScenarioReport)")
    public void makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        var report = new ScenarioReport();
        var scenario = (Scenario) joinPoint.getArgs()[0];
        var webDriverInfo = joinPoint.getArgs()[1].toString();
        report.setScenario(scenario);
        report.setWebDriverInfo(webDriverInfo);
        report.setStartTime(LocalDateTime.now());
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
