package executor.service.aop.report.aspect;

import executor.service.execution.exception.ScenarioExecutionException;
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
@Order(3)
public class ScenarioReportAspect {

    @Around("execution(* executor.service.execution.scenario.ScenarioExecutor.execute(..))")
    public void makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        ScenarioReport report = createReport(joinPoint);
        Scenario scenario = (Scenario) joinPoint.getArgs()[0];
        proceed(joinPoint, scenario, report);
    }

    private void proceed(ProceedingJoinPoint joinPoint, Scenario scenario, ScenarioReport report) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (ScenarioExecutionException ex) {
            report.setErrorMessage(ex.getMessage());
            throw ex;
        } finally {
            report.setEndTime(LocalDateTime.now());
            scenario.setReport(report);
        }
    }

    private ScenarioReport createReport(ProceedingJoinPoint joinPoint) {
        var report = new ScenarioReport();
        var webDriverInfo = joinPoint.getArgs()[1].toString();
        report.setWebDriverInfo(webDriverInfo);
        report.setStartTime(LocalDateTime.now());
        return report;
    }
}
