package executor.service.stepexecution;

import executor.service.annotation.Logged;
import executor.service.exception.scenario.step.SleepException;
import executor.service.model.StepDto;
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
    public void step(WebDriver webDriver, StepDto stepDto) {
        try {
            Thread.sleep(getDuration(stepDto));
        } catch (InterruptedException e) {
            throw new SleepException(e);
        }
    }

    private int getDuration(StepDto step) {
        try {
            String[] values = step.getValue().split(":");
            int first = Integer.parseInt(values[0]);
            int second = Integer.parseInt(values[1]);
            return new Random().nextInt(second - first + 1) + first;
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            throw new SleepException(ex);
        }
    }
}
