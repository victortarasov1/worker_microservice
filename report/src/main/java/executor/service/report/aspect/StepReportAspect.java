package executor.service.report.aspect;

import executor.service.collection.map.step.StepReportMapHandler;
import executor.service.model.Step;
import executor.service.model.StepReport;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Aspect
@RequiredArgsConstructor
public class StepReportAspect {

    private final StepReportMapHandler handler;

    @Around("@within(executor.service.report.annotation.StepReport)")
    public void makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        var step = (Step) joinPoint.getArgs()[1];
        var report = new StepReport();
        report.setStep(step);
        report.setStartTime(LocalTime.now());
        try {
            joinPoint.proceed();
        } catch (Exception ex) {
            report.setErrorMessage(ex.getMessage());
            throw ex;
        } finally {
            report.setEndTime(LocalTime.now());
            handler.put(report.getScenarioUUID(), report);
        }

    }
}
