package executor.service.scenario.step.click;

import executor.service.exception.step.click.ClickNameException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClickNameTest {
    private WebDriver mockWebDriver;

    private ClickName clickName;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickName = new ClickName();
        step = new Step("clickName", "name");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.name(step.getValue())))
                .thenReturn(mockElement);
        clickName.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.name(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickNameException() {
        when(mockWebDriver.findElement(By.name(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickName.step(mockWebDriver, step))
                .isInstanceOf(ClickNameException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickNameException() {
        when(mockWebDriver.findElement(By.name(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickName.step(mockWebDriver, step))
                .isInstanceOf(ClickNameException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickNameException() {
        when(mockWebDriver.findElement(By.name(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickName.step(mockWebDriver, step))
                .isInstanceOf(ClickNameException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickNameException() {
        when(mockWebDriver.findElement(By.name(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickName.step(mockWebDriver, step))
                .isInstanceOf(ClickNameException.class);
    }

    @Test
    public void getStepAction() {
        var stepAction = clickName.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickNameException() {
        when(mockWebDriver.findElement(By.name(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickName.step(mockWebDriver, step))
                .isInstanceOf(ClickNameException.class);
    }
}