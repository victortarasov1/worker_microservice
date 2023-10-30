package executor.service.model;

import java.util.UUID;

public record Step(UUID scenarioUUID, String action, String value) {
}
