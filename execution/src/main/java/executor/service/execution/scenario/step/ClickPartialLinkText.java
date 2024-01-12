package executor.service.execution.scenario.step;

import executor.service.aop.logger.annotation.Logged;
import executor.service.execution.exception.step.ClickPartialLinkTextException;
import executor.service.model.Step;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

@Component
public class ClickPartialLinkText implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickPartialLinkText";
    }

    @Override
    @Logged
    public void step(WebDriver webDriver, Step step) {
        try {
            webDriver.findElement(By.partialLinkText(step.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickPartialLinkTextException(ex);
        }
    }
}
