package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
import executor.service.queue.listener.scenario.ScenarioQueueListener;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void execute(WebDriver webDriver, ScenarioQueueListener listener, ScenarioExecutor scenarioExecutor) {
        Scenario scenario = listener.poll();
        while (scenario != null) {
            scenarioExecutor.execute(scenario, webDriver);
            scenario = listener.poll();
        }
        webDriver.close();
    }

}
