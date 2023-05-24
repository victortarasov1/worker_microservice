package executor.service;

import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import executor.service.stepexecution.ClickCss;
import executor.service.stepexecution.ClickXpath;
import executor.service.stepexecution.Sleep;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ScenarioExecutorImpl implements ScenarioExecutor {
    private final ClickXpath clickXpath;
    private final ClickCss clickCss;
    private final Sleep sleep;

    public ScenarioExecutorImpl(ClickXpath clickXpath, ClickCss clickCss, Sleep sleep) {
        this.clickXpath = clickXpath;
        this.clickCss= clickCss;
        this.sleep = sleep;
    }
    @Override
    public void execute(ScenarioDto scenarioDto, WebDriver webDriver) {
        webDriver.get(scenarioDto.getSite());
        List<StepDto> steps = scenarioDto.getSteps();
        for (StepDto step : steps) {
            if("clickXpath".equalsIgnoreCase(step.getAction())) {
                clickXpath.step(webDriver, step);
            } else if ("clickCss".equalsIgnoreCase(step.getAction())) {
                clickCss.step(webDriver, step);
            } else if ("sleep".equalsIgnoreCase(step.getAction())) {
                sleep.step(webDriver, step);
            }
        }
        webDriver.quit();
    }


}
