package executor.service.stepexecution;

import executor.service.model.StepDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ClickCss implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    public void step(WebDriver webDriver, StepDto stepDto) {
        webDriver.findElement(By.cssSelector(stepDto.getValue())).click();
    }
}
