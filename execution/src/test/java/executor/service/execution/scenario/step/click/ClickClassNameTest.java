package executor.service.execution.scenario.step.click;

import executor.service.execution.exception.step.click.ClickClassNameException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClickClassNameTest {
    private WebDriver mockWebDriver;

    private ClickClassName clickClassName;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickClassName = new ClickClassName();
        step = new Step("clickClassName", "className");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
            var mockElement = Mockito.mock(WebElement.class);
            when(mockWebDriver.findElement(By.className(step.getValue())))
                    .thenReturn(mockElement);
            clickClassName.step(mockWebDriver, step);
            verify(mockWebDriver).findElement(By.className(step.getValue()));
            verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickClassNameException() {
        when(mockWebDriver.findElement(By.className(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickClassName.step(mockWebDriver, step))
                .isInstanceOf(ClickClassNameException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickClassNameException() {
        when(mockWebDriver.findElement(By.className(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickClassName.step(mockWebDriver, step))
                .isInstanceOf(ClickClassNameException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickClassNameException() {
        when(mockWebDriver.findElement(By.className(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickClassName.step(mockWebDriver, step))
                .isInstanceOf(ClickClassNameException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickClassNameException() {
        when(mockWebDriver.findElement(By.className(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickClassName.step(mockWebDriver, step))
                .isInstanceOf(ClickClassNameException.class);
    }

    @Test
    public void getStepAction() {
        var stepAction = clickClassName.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickClassNameException() {
        when(mockWebDriver.findElement(By.className(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickClassName.step(mockWebDriver, step))
                .isInstanceOf(ClickClassNameException.class);
    }
}