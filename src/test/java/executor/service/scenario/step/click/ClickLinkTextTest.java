package executor.service.scenario.step.click;

import executor.service.exception.step.click.ClickLinkTextException;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClickLinkTextTest {
    private WebDriver mockWebDriver;

    private ClickLinkText clickLinkText;
    private Step step;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickLinkText = new ClickLinkText();
        step = new Step("clickLinkText", "text");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        var mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.linkText(step.getValue())))
                .thenReturn(mockElement);
        clickLinkText.step(mockWebDriver, step);
        verify(mockWebDriver).findElement(By.linkText(step.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickLinkTextException() {
        when(mockWebDriver.findElement(By.linkText(step.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickLinkTextException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickLinkTextException() {
        when(mockWebDriver.findElement(By.linkText(step.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickLinkTextException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickLinkTextException() {
        when(mockWebDriver.findElement(By.linkText(step.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickLinkTextException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickLinkTextException() {
        when(mockWebDriver.findElement(By.linkText(step.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickLinkTextException.class);
    }

    @Test
    public void getStepAction() {
        var stepAction = clickLinkText.getStepAction();
        assertThat(stepAction).isEqualTo(step.getAction());
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickLinkTextException() {
        when(mockWebDriver.findElement(By.linkText(step.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickLinkText.step(mockWebDriver, step))
                .isInstanceOf(ClickLinkTextException.class);
    }
}