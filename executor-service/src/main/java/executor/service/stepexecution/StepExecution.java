package executor.service.stepexecution;

import executor.service.model.StepDto;
import org.openqa.selenium.WebDriver;

public interface StepExecution {

    String getStepAction();

    void step(WebDriver webDriver, StepDto stepDto);
}
