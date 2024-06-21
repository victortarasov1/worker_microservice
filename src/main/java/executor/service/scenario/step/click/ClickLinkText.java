package executor.service.scenario.step.click;


import executor.service.exception.step.click.ClickLinkTextException;
import executor.service.logging.annotation.Logged;
import executor.service.model.Step;
import executor.service.scenario.step.StepExecution;
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
