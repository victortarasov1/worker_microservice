package executor.service.scenario;


import executor.service.exception.step.StepExecutionException;
import executor.service.exception.step.UnknownStepException;
import executor.service.logging.annotation.Logged;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import executor.service.model.Step;
import executor.service.model.StepReport;
import executor.service.scenario.step.StepExecution;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScenarioExecutorImpl implements ScenarioExecutor {
    private final Map<String, StepExecution> stepExecutionMap;

    public ScenarioExecutorImpl(List<StepExecution> steps) {
        this.stepExecutionMap = new ConcurrentHashMap<>(steps.stream()
                .collect(Collectors.toMap(StepExecution::getStepAction, Function.identity())));
    }

    @Override
    @Logged
    public ScenarioReport execute(Scenario scenario, WebDriver webDriver) {
        var startTime = LocalDateTime.now();
        var navigationErrorMessage = navigateToSite(scenario.getSite(), webDriver);
        if(navigationErrorMessage != null || scenario.getSteps().isEmpty())
            return new ScenarioReport(scenario.getId(), startTime, LocalDateTime.now(), navigationErrorMessage, webDriver.toString(), List.of());
        var reports = executeScenarioSteps(scenario.getSteps(), webDriver);
        var errorMessage = reports.get(reports.size() - 1).errorMessage();
        return new ScenarioReport(scenario.getId(), startTime, LocalDateTime.now(), errorMessage, webDriver.toString(), reports);
    }

    private List<StepReport> executeScenarioSteps(List<Step> steps, WebDriver webDriver) {
        var reports = new ArrayList<StepReport>();
        for (var step : steps) {
            var report = createStepReport(step, webDriver);
            reports.add(report);
            if (report.errorMessage() != null) return reports;
        }
        return reports;
    }

    private String navigateToSite(String url, WebDriver webDriver) {
        try {
            webDriver.get(url);
        } catch (WebDriverException ex) {
            return "Failed to navigate to site: " + ex.getMessage();
        }
        return null;
    }

    private StepReport createStepReport(Step step, WebDriver driver) {
        var startTime = LocalTime.now();
        try {
            executeStep(step, driver);
        } catch (StepExecutionException ex) {
            return new StepReport(startTime, LocalTime.now(), ex.getMessage());
        }
        return new StepReport(startTime, LocalTime.now(), null);

    }

    private void executeStep(Step step, WebDriver driver) {
        var stepExecution = stepExecutionMap.get(step.getAction());
        if (stepExecution != null) stepExecution.step(driver, step);
        else throw new UnknownStepException(step.getAction());
    }
}

