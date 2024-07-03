package executor.service.facade;

import executor.service.client.ProxyClient;
import executor.service.factory.WebDriverProvider;
import executor.service.logging.annotation.Logged;
import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;
import executor.service.scenario.ScenarioExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerFacadeImpl implements WorkerFacade {
    private final ScenarioExecutor executor;
    private final WebDriverProvider provider;
    private final ProxyClient client;
    @Override
    @Logged
    public ScenarioReport execute(Scenario scenario) {
        var proxy = client.get();
        var webDriver = provider.create(proxy);
        var scenarioReport = executor.execute(scenario, webDriver);
        webDriver.quit();
        return scenarioReport;
    }
}
