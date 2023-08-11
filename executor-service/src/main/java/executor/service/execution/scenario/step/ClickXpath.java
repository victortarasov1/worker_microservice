package executor.service.execution.scenario.step;

import executor.service.annotation.Logged;
import executor.service.exception.scenario.step.ClickXPathException;
import executor.service.model.StepDto;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

@Component
@Logged
public class ClickXpath implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, StepDto stepDto) {
        try {
            webDriver.findElement(By.xpath(stepDto.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickXPathException(ex);
        }
    }
}
