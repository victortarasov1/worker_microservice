package executor.service.stepexecution;

import executor.service.exception.SleepException;
import executor.service.model.StepDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class Sleep implements StepExecution {
    @Override
    public String getStepAction() {
        return "sleep";
    }

    @Override
    public void step(WebDriver webDriver, StepDto stepDto) {
        new WebDriverWait(webDriver, getDuration(stepDto))
                .until(v -> true);
    }

    private Duration getDuration(StepDto step) {
        try {
            String[] values = step.getValue().split(":");
            int first = Integer.parseInt(values[0]);
            int second = Integer.parseInt(values[1]);
            return Duration.ofMillis(new Random().nextInt(second - first + 1) + first);
        } catch ( ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            throw new SleepException(ex.getMessage());
        }
    }
}
