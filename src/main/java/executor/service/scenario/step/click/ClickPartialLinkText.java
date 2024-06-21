package executor.service.scenario.step.click;


import executor.service.exception.step.click.ClickPartialLinkTextException;
import executor.service.logging.annotation.Logged;
import executor.service.model.Step;
import executor.service.scenario.step.StepExecution;
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
