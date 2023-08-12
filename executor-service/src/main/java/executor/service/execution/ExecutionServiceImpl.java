package executor.service.execution;

import executor.service.execution.scenario.ScenarioExecutor;

import executor.service.model.ScenarioDto;
import executor.service.source.SourceListener;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void execute(WebDriver webDriver, SourceListener<ScenarioDto> scenarioSourceListener,
                        ScenarioExecutor scenarioExecutor) {

        Optional<ScenarioDto> scenario = scenarioSourceListener.getOne();

        while(scenario.isPresent()) {
            scenarioExecutor.execute(scenario.get(), webDriver);
            scenario = scenarioSourceListener.getOne();
        }

        webDriver.close();
    }

}
