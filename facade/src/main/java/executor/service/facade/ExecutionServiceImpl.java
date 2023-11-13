package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void execute(WebDriver webDriver, Queue<Scenario> scenarios, ScenarioExecutor scenarioExecutor) {
        scenarios.forEach(scenario -> scenarioExecutor.execute(scenario, webDriver));
        webDriver.close();
    }

}
