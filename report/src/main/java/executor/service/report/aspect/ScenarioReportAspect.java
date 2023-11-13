package executor.service.report.aspect;

import executor.service.execution.exception.ScenarioExecutionException;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import executor.service.redis.repository.ScenarioReportRepository;
import executor.service.redis.repository.ScenarioRepository;
import executor.service.redis.repository.StepRepository;
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
@Order(2)
public class ScenarioReportAspect {
    private final ScenarioRepository scenarioRepository;
    private final StepRepository stepRepository;
    private final ScenarioReportRepository scenarioReportRepository;

    @Around("execution(* executor.service.execution.scenario.ScenarioExecutor.execute(..))")
    public void makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        ScenarioReport report = createReport(joinPoint);
        Scenario scenario = (Scenario) joinPoint.getArgs()[0];
        saveScenario(scenario);
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
            saveReport(scenario, report);
        }
    }

    private ScenarioReport createReport(ProceedingJoinPoint joinPoint) {
        var report = new ScenarioReport();
        var webDriverInfo = joinPoint.getArgs()[1].toString();
        report.setWebDriverInfo(webDriverInfo);
        report.setStartTime(LocalDateTime.now());
        return report;
    }

    private void saveReport(Scenario scenario, ScenarioReport report) {
        scenario.setReport(report);
        scenarioReportRepository.save(report);
        scenarioRepository.save(scenario);
    }

    private void saveScenario(Scenario scenario) {
        stepRepository.saveAll(scenario.getSteps());
        scenarioRepository.save(scenario);
    }
}
