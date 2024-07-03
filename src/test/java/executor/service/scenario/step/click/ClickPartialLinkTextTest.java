package executor.service.scenario.step.click;

import executor.service.exception.step.click.ClickPartialLinkTextException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClickPartialLinkTextTest {
    private WebDriver mockWebDriver;

    private ClickPartialLinkText clickPartialLinkText;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickPartialLinkText = new ClickPartialLinkText();
        step = new Step("clickPartialLinkText", "text");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.partialLinkText(step.getValue())))
                .thenReturn(mockElement);
        clickPartialLinkText.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.partialLinkText(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickPartialLinkTextException() {
        when(mockWebDriver.findElement(By.partialLinkText(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickPartialLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickPartialLinkTextException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickPartialLinkTextException() {
        when(mockWebDriver.findElement(By.partialLinkText(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickPartialLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickPartialLinkTextException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickPartialLinkTextException() {
        when(mockWebDriver.findElement(By.partialLinkText(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickPartialLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickPartialLinkTextException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickPartialLinkTextException() {
        when(mockWebDriver.findElement(By.partialLinkText(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickPartialLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickPartialLinkTextException.class);
    }

    @Test
    public void getStepAction() {
        var stepAction = clickPartialLinkText.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickPartialLinkTextException() {
        when(mockWebDriver.findElement(By.partialLinkText(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickPartialLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickPartialLinkTextException.class);
    }
}