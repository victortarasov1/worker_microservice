package executor.service.maintenance;


import executor.service.annotation.Logged;
import executor.service.exception.scenario.SiteNotFoundException;
import executor.service.exception.scenario.step.UnknownStepException;
import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import executor.service.stepexecution.StepExecution;
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
public class ScenarioExecutorImpl implements ScenarioExecutor {
    private final Map<String, StepExecution> stepExecutionMap;

    public ScenarioExecutorImpl(List<StepExecution> steps) {
        this.stepExecutionMap = new ConcurrentHashMap<>(steps.stream()
                .collect(Collectors.toMap(StepExecution::getStepAction, Function.identity())));
    }

    @Override
    public void execute(ScenarioDto scenarioDto, WebDriver webDriver) {
        goToSite(scenarioDto.getSite(), webDriver);
        scenarioDto.getSteps().forEach(v -> executeStep(v, webDriver));
    }

    private void goToSite(String url, WebDriver webDriver) {
        try {
            webDriver.get(url);
        } catch (WebDriverException ex) {
            throw new SiteNotFoundException(url, ex);
        }
    }

    private void executeStep(StepDto step, WebDriver driver) {
        Optional.ofNullable(stepExecutionMap.get(step.getAction()))
                .orElseThrow(() -> new UnknownStepException(step.getAction()))
                .step(driver, step);
    }
}

