package executor.service.aop.report.aspect;

import executor.service.execution.exception.step.StepExecutionException;
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


    @Around("execution(* executor.service.execution.scenario.step.StepExecution.step(..))")
    public void makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        StepReport report = createReport();
        proceed(joinPoint, report);
    }


    private void proceed(ProceedingJoinPoint joinPoint, StepReport report) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (StepExecutionException ex) {
            report.setErrorMessage(ex.getMessage() + "\n Caused By " + ex.getCause().getMessage());
            throw ex;
        } finally {
            report.setEndTime(LocalTime.now());
            var step = (Step) joinPoint.getArgs()[1];
            step.setReport(report);
        }
    }

    private StepReport createReport() {
        var report = new StepReport();
        report.setStartTime(LocalTime.now());
        return report;
    }
}
