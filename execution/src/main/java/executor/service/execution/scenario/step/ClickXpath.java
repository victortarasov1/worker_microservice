package executor.service.execution.scenario.step;

import executor.service.logger.annotation.Logged;
import executor.service.execution.exception.step.ClickXPathException;
import executor.service.model.Step;
import executor.service.report.annotation.StepReport;
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
    @StepReport
    public void step(WebDriver webDriver, Step step) {
        try {
            webDriver.findElement(By.xpath(step.value())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickXPathException(ex);
        }
    }
}
