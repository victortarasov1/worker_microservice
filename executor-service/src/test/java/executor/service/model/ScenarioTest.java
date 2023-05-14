package executor.service.model;

import org.junit.jupiter.api.Assertions;
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

    @Test
    public void settersTest() {
        ArrayList<StepDto> list = new ArrayList<>();
        ScenarioDto scenarioDto = new ScenarioDto();
        scenarioDto.setName("name");
        scenarioDto.setSite("site");
        scenarioDto.setSteps(list);
        ScenarioDto exceptedDto = new ScenarioDto("name", "site", list);
        Assertions.assertEquals(scenarioDto, exceptedDto);
    }

    @Test
    public void gettersTest() {
        ArrayList<StepDto> list = new ArrayList<>();
        ScenarioDto scenarioDto = new ScenarioDto();
        ScenarioDto actualDto = new ScenarioDto("name", "site", list);
        scenarioDto.setName(actualDto.getName());
        scenarioDto.setSite(actualDto.getSite());
        scenarioDto.setSteps(actualDto.getSteps());
        Assertions.assertEquals(actualDto, scenarioDto);
    }
}
