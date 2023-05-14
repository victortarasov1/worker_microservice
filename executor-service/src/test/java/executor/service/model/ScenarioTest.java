package executor.service.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ScenarioTest {
    @Test
    public void testEqualsAndHaschCode() {
        ScenarioDto scenario1 = new ScenarioDto("name", "site", new ArrayList<>());
        ScenarioDto scenario2 = new ScenarioDto("name", "site", new ArrayList<>());
        assertEquals(scenario1, scenario2);
        assertEquals(scenario1.hashCode(), scenario2.hashCode());
    }

}
