package executor.service.exectuion.scenario.step;

import executor.service.model.StepDto;
import org.openqa.selenium.WebDriver;

public interface StepExecution {

    String getStepAction();

    void step(WebDriver webDriver, StepDto stepDto);
}
