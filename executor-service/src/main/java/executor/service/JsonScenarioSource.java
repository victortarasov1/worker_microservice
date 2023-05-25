package executor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ScenarioDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonScenarioSource implements ScenarioSource {
    private String resourceName = "scenarios.json";
    private final String resourceFolder = "src/main/resources/";

    public JsonScenarioSource() {}

    public JsonScenarioSource(String newResourceName) {
        resourceName = newResourceName;
    }

    public List<ScenarioDto> getScenarios() {
        ObjectMapper mapper = new ObjectMapper();
        List<ScenarioDto> scenarioDtos = new ArrayList<>();

        try {
            scenarioDtos = mapper.readValue(getFile(), new TypeReference<>() {});
        } catch (IOException e)  {
            e.printStackTrace();
        }

        return scenarioDtos;
    }

    private File getFile() {
        Path path = Paths.get(resourceName);
        File file;

        if (path.isAbsolute()) {
            file = path.toFile();
        } else {
            file = Paths.get(resourceFolder + resourceName).toFile();
        }

        return file;
    }
}
