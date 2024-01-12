package executor.service.execution.scenario.step.click;

import executor.service.execution.exception.step.click.ClickTagNameException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClickTagNameTest {
    private WebDriver mockWebDriver;

    private ClickTagName clickTagName;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickTagName = new ClickTagName();
        step = new Step("clickTagName", "name");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.tagName(step.getValue())))
                .thenReturn(mockElement);
        clickTagName.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.tagName(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickTagNameException() {
        when(mockWebDriver.findElement(By.tagName(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickTagName.step(mockWebDriver, step))
                .isInstanceOf(ClickTagNameException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickTagNameException() {
        when(mockWebDriver.findElement(By.tagName(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickTagName.step(mockWebDriver, step))
                .isInstanceOf(ClickTagNameException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickTagNameException() {
        when(mockWebDriver.findElement(By.tagName(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickTagName.step(mockWebDriver, step))
                .isInstanceOf(ClickTagNameException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickTagNameException() {
        when(mockWebDriver.findElement(By.tagName(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickTagName.step(mockWebDriver, step))
                .isInstanceOf(ClickTagNameException.class);
    }

    @Test
    public void getStepAction() {
        var stepAction = clickTagName.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickTagNameException() {
        when(mockWebDriver.findElement(By.tagName(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickTagName.step(mockWebDriver, step))
                .isInstanceOf(ClickTagNameException.class);
    }
}