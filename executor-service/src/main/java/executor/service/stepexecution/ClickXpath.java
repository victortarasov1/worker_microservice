package executor.service.stepexecution;

import executor.service.model.StepDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ClickXpath implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, StepDto stepDto) {
        webDriver.findElement(By.xpath(stepDto.getValue())).click();
    }
}
