package executor.service.execution.scenario.step;

import executor.service.exception.step.SleepException;
import executor.service.model.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class SleepTest {

    private WebDriver mockWebDriver;

    private Sleep sleep;

    @BeforeEach
    public void setup() {
        mockWebDriver = Mockito.mock(WebDriver.class);
        sleep = new Sleep();
    }

    @Test
    public void step_ShouldSleepForRandomDuration() {
        Step step = new Step("sleep", "100:200");
        assertTimeoutPreemptively(Duration.ofMillis(300), () -> sleep.step(mockWebDriver, step));
    }

    @Test
    public void step_WhenInvalidFormat_ShouldThrowSleepException() {
        Step step = new Step("sleep", "abc:def");
        assertThatThrownBy(() -> sleep.step(mockWebDriver, step))
                .isInstanceOf(SleepException.class);
    }

    @Test
    public void step_WhenMissingColon_ShouldThrowSleepException() {
        Step step = new Step("sleep", "100200");
        assertThatThrownBy(() -> sleep.step(mockWebDriver, step))
                .isInstanceOf(SleepException.class);
    }

    @Test
    public void step_WhenNegativeDuration_ShouldThrowSleepException() {
        Step step = new Step("sleep", "200:100");
        assertThatThrownBy(() -> sleep.step(mockWebDriver, step))
                .isInstanceOf(SleepException.class);
    }

    @Test
    public void getStepAction_ShouldReturnSleep() {
        String stepAction = sleep.getStepAction();
        assertThat(stepAction).isEqualTo("sleep");
    }

    @Test
    public void step_WhenInterruptedException_ShouldThrowSleepException() {
        Step step = new Step("sleep", "100:200");
        Thread.currentThread().interrupt();
        Assertions.assertThrows(SleepException.class, () -> sleep.step(mockWebDriver, step));
    }
}