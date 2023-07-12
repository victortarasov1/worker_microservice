package executor.service;

import executor.service.model.ScenarioDto;
import executor.service.utl.JsonReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonScenarioSource implements ScenarioSource {
    private String resourceName = "scenarios.json";

    public JsonScenarioSource() {}

    public JsonScenarioSource(String newResourceName) {
        resourceName = newResourceName;
    }

    public List<ScenarioDto> getScenarios() {
        return JsonReader.parseResourceToList(resourceName, ScenarioDto.class);
    }
}
