package executor.service.execution.scenario.step.click;

import executor.service.aop.logger.annotation.Logged;
import executor.service.execution.exception.step.click.ClickLinkTextException;
import executor.service.execution.scenario.step.StepExecution;
import executor.service.model.Step;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

@Component
public class ClickLinkText implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickLinkText";
    }

    @Override
    @Logged
    public void step(WebDriver webDriver, Step step) {
        try {
            webDriver.findElement(By.linkText(step.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickLinkTextException(ex);
        }
    }
}
