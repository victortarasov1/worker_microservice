package executor.service.facade;

import executor.service.execution.scenario.ScenarioExecutor;
import executor.service.model.Scenario;
import executor.service.queue.consumer.scenario.ScenarioConsumer;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void execute(WebDriver webDriver, ScenarioConsumer listener, ScenarioExecutor scenarioExecutor) {
        Scenario scenario = listener.poll();
        while (scenario != null) {
            scenarioExecutor.execute(scenario, webDriver);
            scenario = listener.poll();
        }
        webDriver.close();
    }

}
