package executor.service.stepexecution;

import executor.service.model.StepDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
public class SleepTest {

    @Mock
    private WebDriver mockWebDriver;

    private Sleep sleep;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sleep = new Sleep();
    }

    @Test
    public void step_ShouldSleepForRandomDuration() {
        StepDto stepDto = new StepDto("sleep", "100:200");

        assertTimeoutPreemptively(Duration.ofMillis(300), () -> sleep.step(mockWebDriver, stepDto));
    }

    @Test
    public void step_WhenInvalidFormat_ShouldThrowNumberFormatException() {
        StepDto stepDto = new StepDto("sleep", "abc:def");

        assertThatThrownBy(() -> sleep.step(mockWebDriver, stepDto))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    public void step_WhenMissingColon_ShouldThrowArrayIndexOutOfBoundsException() {
        StepDto stepDto = new StepDto("sleep", "100200");

        assertThatThrownBy(() -> sleep.step(mockWebDriver, stepDto))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    public void step_WhenNegativeDuration_ShouldThrowIllegalArgumentException() {
        StepDto stepDto = new StepDto("sleep", "200:100");

        assertThatThrownBy(() -> sleep.step(mockWebDriver, stepDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getStepAction_ShouldReturnSleep() {
        String stepAction = sleep.getStepAction();
        assertThat(stepAction).isEqualTo("sleep");
    }

}