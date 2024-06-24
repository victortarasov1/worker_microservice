package executor.service.scenario.step.click;

import executor.service.exception.step.click.ClickIdException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClickIdTest {
    private WebDriver mockWebDriver;

    private ClickId clickId;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickId = new ClickId();
        step = new Step("clickId", "id");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.id(step.getValue())))
                .thenReturn(mockElement);
        clickId.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.id(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickIdException() {
        when(mockWebDriver.findElement(By.id(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickId.step(mockWebDriver, step))
                .isInstanceOf(ClickIdException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickIdException() {
        when(mockWebDriver.findElement(By.id(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickId.step(mockWebDriver, step))
                .isInstanceOf(ClickIdException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickIdException() {
        when(mockWebDriver.findElement(By.id(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickId.step(mockWebDriver, step))
                .isInstanceOf(ClickIdException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickIdException() {
        when(mockWebDriver.findElement(By.id(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickId.step(mockWebDriver, step))
                .isInstanceOf(ClickIdException.class);
    }

    @Test
    public void getStepAction() {
        var stepAction = clickId.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickIdException() {
        when(mockWebDriver.findElement(By.id(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickId.step(mockWebDriver, step))
                .isInstanceOf(ClickIdException.class);
    }
}