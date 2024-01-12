package executor.service.execution.scenario.step.click;

import executor.service.execution.exception.step.click.ClickXPathException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickXpathTest {

    private WebDriver mockWebDriver;

    private ClickXpath clickXpath;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickXpath = new ClickXpath();
        step = new Step("clickXpath", "xpathExpression");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.xpath(step.getValue())))
                .thenReturn(mockElement);
        clickXpath.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.xpath(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickXPathException()  {
        when(mockWebDriver.findElement(By.xpath(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickXPathException()  {
        when(mockWebDriver.findElement(By.xpath(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }
    @Test
    public void getStepAction_ShouldReturnClickXpath() {
        var stepAction = clickXpath.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

}