package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.ScenarioDto;
import executor.service.source.SourceListener;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, SourceListener<ScenarioDto> scenarioSourceListener,
                 ScenarioExecutor scenarioExecutor);
}
