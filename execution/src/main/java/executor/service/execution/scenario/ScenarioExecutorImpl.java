package executor.service.execution.scenario;


import executor.service.logger.annotation.HandleException;
import executor.service.logger.annotation.Logged;
import executor.service.execution.exception.SiteNotFoundException;
import executor.service.execution.exception.step.UnknownStepException;
import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.execution.scenario.step.StepExecution;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Logged
@HandleException
public class ScenarioExecutorImpl implements ScenarioExecutor {
    private final Map<String, StepExecution> stepExecutionMap;

    public ScenarioExecutorImpl(List<StepExecution> steps) {
        this.stepExecutionMap = new ConcurrentHashMap<>(steps.stream()
                .collect(Collectors.toMap(StepExecution::getStepAction, Function.identity())));
    }

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        goToSite(scenario.getSite(), webDriver);
        scenario.getSteps().forEach(v -> executeStep(v, webDriver));
    }

    private void goToSite(String url, WebDriver webDriver) {
        try {
            webDriver.get(url);
        } catch (WebDriverException ex) {
            throw new SiteNotFoundException(url, ex);
        }
    }

    private void executeStep(Step step, WebDriver driver) {
        Optional.ofNullable(stepExecutionMap.get(step.getAction()))
                .orElseThrow(() -> new UnknownStepException(step.getAction()))
                .step(driver, step);
    }
}

