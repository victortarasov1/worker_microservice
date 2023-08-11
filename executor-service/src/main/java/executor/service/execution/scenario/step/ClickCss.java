package executor.service.execution.scenario.step;

import executor.service.annotation.Logged;
import executor.service.exception.scenario.step.ClickCssException;
import executor.service.model.StepDto;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

@Component
@Logged
public class ClickCss implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    public void step(WebDriver webDriver, StepDto stepDto) {
        try {
            webDriver.findElement(By.cssSelector(stepDto.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickCssException(ex);
        }
    }
}
