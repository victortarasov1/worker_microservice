package executor.service.execution.scenario.step.click;

import executor.service.execution.exception.step.click.ClickCssException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickCssTest {

    private WebDriver mockWebDriver;

    private ClickCss clickCss;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickCss = new ClickCss();
        step = new Step("clickCss", "cssSelector");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.cssSelector(step.getValue())))
                .thenReturn(mockElement);
        clickCss.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.cssSelector(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, step))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, step))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, step))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, step))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void getStepAction_ShouldReturnClickCss() {
        var stepAction = clickCss.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, step))
                .isInstanceOf(ClickCssException.class);
    }

}