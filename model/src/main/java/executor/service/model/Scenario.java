package executor.service.model;

import java.util.List;
import java.util.UUID;

public record Scenario(UUID uuid, String name, String site, List<Step> steps) {
}
