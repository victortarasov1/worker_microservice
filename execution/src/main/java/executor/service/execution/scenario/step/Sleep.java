package executor.service.execution.scenario.step;

import executor.service.logger.annotation.Logged;
import executor.service.execution.exception.step.SleepException;
import executor.service.model.Step;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
@Logged
public class Sleep implements StepExecution {
    @Override
    public String getStepAction() {
        return "sleep";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            Thread.sleep(getDuration(step));
        } catch (InterruptedException e) {
            throw new SleepException(e);
        }
    }

    private int getDuration(Step step) {
        try {
            String[] values = step.value().split(":");
            int first = Integer.parseInt(values[0]);
            int second = Integer.parseInt(values[1]);
            return new Random().nextInt(second - first + 1) + first;
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            throw new SleepException(ex);
        }
    }
}
