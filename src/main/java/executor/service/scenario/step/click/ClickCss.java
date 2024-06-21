package executor.service.scenario.step.click;

import executor.service.exception.step.click.ClickCssException;
import executor.service.logging.annotation.Logged;
import executor.service.model.Step;
import executor.service.scenario.step.StepExecution;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;


@Component
public class ClickCss implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    @Logged
    public void step(WebDriver webDriver, Step step) {
        try {
            webDriver.findElement(By.cssSelector(step.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickCssException(ex);
        }
    }
}
