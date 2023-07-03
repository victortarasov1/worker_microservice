package executor.service;

import executor.service.model.ScenarioDto;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public class ExecutionService {

    public void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                        ScenarioExecutor scenarioExecutor) {

        Optional<ScenarioDto> scenario = scenarioSourceListener.getScenario();

        while(scenario.isPresent()) {
            scenarioExecutor.execute(scenario.get(), webDriver);
            scenario = scenarioSourceListener.getScenario();
        }
    }

}
