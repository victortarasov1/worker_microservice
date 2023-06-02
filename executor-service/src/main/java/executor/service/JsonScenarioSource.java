package executor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exception.ResourceFileNotFoundException;
import executor.service.model.ScenarioDto;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class JsonScenarioSource implements ScenarioSource {
    private String resourceName = "scenarios.json";

    public JsonScenarioSource() {}

    public JsonScenarioSource(String newResourceName) {
        resourceName = newResourceName;
    }

    public List<ScenarioDto> getScenarios() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(getFile(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NullPointerException e) {
            throw new ResourceFileNotFoundException(resourceName);
        }
    }

    private File getFile() {
        Path resourcePath = Paths.get(resourceName);

        if (resourcePath.isAbsolute()) {
            return resourcePath.toFile();
        }

        URL url = Objects.requireNonNull(this.getClass().getResource(resourceName));

        return new File(url.getFile());
    }
}
