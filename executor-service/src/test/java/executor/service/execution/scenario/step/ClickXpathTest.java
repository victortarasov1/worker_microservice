package executor.service.execution.scenario.step;

import executor.service.exception.scenario.step.ClickXPathException;
import executor.service.exectuion.scenario.step.ClickXpath;
import executor.service.model.StepDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickXpathTest {

    private WebDriver mockWebDriver;

    private ClickXpath clickXpath;
    private StepDto stepDto;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        clickXpath = new ClickXpath();
        stepDto = new StepDto("clickXpath", "xpathExpression");
    }

    @Test
    public void step_WhenElementExists_ShouldClickElement() {
        WebElement mockElement = Mockito.mock(WebElement.class);
        when(mockWebDriver.findElement(By.xpath(stepDto.getValue())))
                .thenReturn(mockElement);
        clickXpath.step(mockWebDriver, stepDto);
        verify(mockWebDriver).findElement(By.xpath(stepDto.getValue()));
        verify(mockElement).click();
    }

    @Test
    public void step_WhenNoSuchElement_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(stepDto.getValue()))).thenThrow(NoSuchElementException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenElementNotInteractable_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(stepDto.getValue()))).thenThrow(ElementNotInteractableException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenStaleElementReference_ShouldThrowClickXPathException()  {
        when(mockWebDriver.findElement(By.xpath(stepDto.getValue()))).thenThrow(StaleElementReferenceException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenTimeoutException_ShouldThrowClickXPathException()  {
        when(mockWebDriver.findElement(By.xpath(stepDto.getValue())))
                .thenThrow(TimeoutException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickXPathException.class);
    }

    @Test
    public void step_WhenInvalidSelector_ShouldThrowClickXPathException() {
        when(mockWebDriver.findElement(By.xpath(stepDto.getValue())))
                .thenThrow(InvalidSelectorException.class);
        assertThatThrownBy(() -> clickXpath.step(mockWebDriver, stepDto))
                .isInstanceOf(ClickXPathException.class);
    }
    @Test
    public void getStepAction_ShouldReturnClickXpath() {
        String stepAction = clickXpath.getStepAction();
        assertThat(stepAction).isEqualTo("clickXpath");
    }

}