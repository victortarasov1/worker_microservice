package executor.service.execution.scenario.step;

import executor.service.execution.exception.step.ClickXPathException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import java.util.UUID;

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
        step = new Step(UUID.randomUUID(), "clickXpath", "xpathExpression");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        WebElement mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.xpath(step.value())))
                .thenReturn(mockElement);
        clickXpath.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.xpath(step.value()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(step.value()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(step.value()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickXPathException()  {
        when(mockWebDriver.findElement(By.xpath(step.value()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickXPathException()  {
        when(mockWebDriver.findElement(By.xpath(step.value())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(step.value())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, step))
                .isInstanceOf(ClickXPathException.class);
    }
    @Test
    public void getStepAction_ShouldReturnClickXpath() {
        String stepAction = clickXpath.getStepAction();
        assertThat(stepAction).isEqualTo("clickXpath");
    }

}