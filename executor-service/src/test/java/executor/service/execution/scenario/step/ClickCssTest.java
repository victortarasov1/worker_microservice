package executor.service.execution.scenario.step;

import executor.service.exception.scenario.step.ClickCssException;
import executor.service.model.StepDto;
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
    private StepDto stepDto;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickCss = new ClickCss();
        stepDto = new StepDto("clickCss", "cssSelector");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        WebElement mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue())))
                .thenReturn(mockElement);
        clickCss.step(mockWebDriver, stepDto);
        verify(mockWebDriver).findElement(By.cssSelector(stepDto.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickCssException.class);
    }

    @Test
    public void getStepAction_ShouldThrowClickCssException() {
        String stepAction = clickCss.getStepAction();
        assertThat(stepAction).isEqualTo("clickCss");
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickCssException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickCssException.class);
    }

}