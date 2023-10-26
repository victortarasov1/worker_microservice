package executor.service.execution.scenario.step;

import executor.service.logger.annotation.Logged;
import executor.service.execution.exception.step.ClickXPathException;
import executor.service.model.Step;
import executor.service.report.annotation.StepReport;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

@Component
@Logged
@StepReport
public class ClickXpath implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            webDriver.findElement(By.xpath(step.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException | InvalidSelectorException ex) {
            throw new ClickXPathException(ex);
        }
    }
}
