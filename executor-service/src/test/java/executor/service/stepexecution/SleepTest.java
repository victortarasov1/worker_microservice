package executor.service.stepexecution;

import executor.service.exception.SleepException;
import executor.service.model.StepDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
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
        StepDto stepDto = new StepDto("sleep", "100:200");
        assertTimeoutPreemptively(Duration.ofMillis(300), () -> sleep.step(mockWebDriver, stepDto));
    }

    @Test
    public void step_WhenInvalidFormat_ShouldThrowSleepException() {
        StepDto stepDto = new StepDto("sleep", "abc:def");
        assertThatThrownBy(() -> sleep.step(mockWebDriver, stepDto))
                .isInstanceOf(SleepException.class);
    }

    @Test
    public void step_WhenMissingColon_ShouldThrowSleepException() {
        StepDto stepDto = new StepDto("sleep", "100200");
        assertThatThrownBy(() -> sleep.step(mockWebDriver, stepDto))
                .isInstanceOf(SleepException.class);
    }

    @Test
    public void step_WhenNegativeDuration_ShouldThrowSleepException() {
        StepDto stepDto = new StepDto("sleep", "200:100");
        assertThatThrownBy(() -> sleep.step(mockWebDriver, stepDto))
                .isInstanceOf(SleepException.class);
    }

    @Test
    public void getStepAction_ShouldReturnSleep() {
        String stepAction = sleep.getStepAction();
        assertThat(stepAction).isEqualTo("sleep");
    }

}