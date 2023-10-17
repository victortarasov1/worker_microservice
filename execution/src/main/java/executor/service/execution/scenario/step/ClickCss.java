package executor.service.execution.scenario.step;

import executor.service.logger.annotation.Logged;
import executor.service.execution.exception.step.ClickCssException;
import executor.service.model.Step;
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
    public void step(WebDriver webDriver, Step step) {
        try {
            webDriver.findElement(By.cssSelector(step.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickCssException(ex);
        }
    }
}
