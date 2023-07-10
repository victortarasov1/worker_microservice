package executor.service.maintenance.plugin.proxy;


import executor.service.annotation.Logged;
import executor.service.exception.scenario.SiteNotFoundException;
import executor.service.exception.scenario.step.UnknownStepException;
import executor.service.maintenance.plugin.proxy.ScenarioExecutor;
import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import executor.service.stepexecution.StepExecution;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
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
        try {
            webDriver.get(scenarioDto.getSite());
        } catch (WebDriverException ex) {
            throw new SiteNotFoundException(scenarioDto.getSite(), ex);
        }
        Consumer<StepDto> executeStep = v -> {
               StepExecution stepExecution = stepExecutionMap
                       .get(v.getAction());
               if(stepExecution == null) throw new UnknownStepException(v.getAction());
               stepExecution.step(webDriver, v);
        };
        scenarioDto.getSteps().forEach(executeStep);
    }
}

