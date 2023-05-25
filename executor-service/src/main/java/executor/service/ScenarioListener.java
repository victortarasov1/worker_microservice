package executor.service;

import executor.service.model.ScenarioDto;
import org.openqa.selenium.WebDriver;

public class ScenarioListener implements ScenarioSourceListener {
    ScenarioExecutor scenarioExecutor;
    WebDriver webDriver;
    ScenarioSource scenarioSource;

    ScenarioListener() {}

    public ScenarioListener(ScenarioExecutor scenarioExecutor, WebDriver webDriver, ScenarioSource scenarioSource) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriver = webDriver;
        this.scenarioSource = scenarioSource;
    }

    @Override
    public void execute() {
        for (ScenarioDto scenarioDto : scenarioSource.getScenarios()) {
            scenarioExecutor.execute(scenarioDto, webDriver);
        }
    }
}
