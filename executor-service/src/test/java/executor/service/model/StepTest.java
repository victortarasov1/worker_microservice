package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StepTest {

    private Step step;

    @BeforeEach
    public void setUp(){
        step = new Step("Test action", "Test value");
    }

    @Test
    public void emptyConstructorTest(){
        Step actualStep = new Step();
        Step expectedStep = new Step();
        Assertions.assertEquals(expectedStep, actualStep);
    }

    @Test
    public void settersTest(){
        step.setAction("Test action 2");
        step.setValue("Test value 2");
        Step expectedStep = new Step("Test action 2", "Test value 2");
        Assertions.assertEquals(step, expectedStep);
    }

    @Test
    public void gettersTest(){
        Step actualStep = new Step("Test action 2", "Test value 2");
        step.setValue(actualStep.getValue());
        step.setAction(actualStep.getAction());
        Assertions.assertEquals(actualStep, step);
    }

    @Test
    public void equalsTest(){
        Step step2 = new Step("Test action 2", "Test value 2");
        boolean result = step.equals(step2);
        Assertions.assertFalse(result);
        step.setAction("Test action 2");
        step.setValue("Test value 2");
        boolean result2 = step.equals(step2);
        Assertions.assertTrue(result2);
    }

}
