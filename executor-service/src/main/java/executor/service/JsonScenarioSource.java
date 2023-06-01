package executor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            throw new RuntimeException("File " + resourceName + " not found in \"resources\" folder");
        }
    }

    private File getFile() {
        Path resourcePath = Paths.get(resourceName);
        File file;

        if (resourcePath.isAbsolute()) {
            file = resourcePath.toFile();
        } else {
            URL url = Objects.requireNonNull(this.getClass().getResource(resourceName));
            file = new File(url.getFile());
        }

        return file;
    }
}
