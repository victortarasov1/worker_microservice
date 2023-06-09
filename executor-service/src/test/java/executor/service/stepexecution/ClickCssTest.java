package executor.service.stepexecution;

import executor.service.model.StepDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickCssTest {

    @Mock
    private WebDriver mockWebDriver;

    private ClickCss clickCss;
    private StepDto stepDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
    public void step_WhenNoSuchElement_ShouldThrowNoSuchElementException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowElementNotInteractableException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(ElementNotInteractableException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowStaleElementReferenceException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(StaleElementReferenceException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowTimeoutException() {
        when(mockWebDriver.findElement(By.cssSelector(stepDto.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickCss.step(mockWebDriver, stepDto))
                .isInstanceOf(TimeoutException.class);
    }

    @Test
    public void getStepAction_ShouldReturnClickCss() {
        String stepAction = clickCss.getStepAction();
        assertThat(stepAction).isEqualTo("clickCss");
    }

}