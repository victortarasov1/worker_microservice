package executor.service.exectuion.scenario;

import executor.service.model.ScenarioDto;
import org.openqa.selenium.WebDriver;

public interface ScenarioExecutor {
    void execute(ScenarioDto scenarioDto, WebDriver webDriver);
}
