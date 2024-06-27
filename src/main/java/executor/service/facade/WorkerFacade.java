package executor.service.facade;

import executor.service.model.Scenario;
import executor.service.model.ScenarioReport;

public interface WorkerFacade {
    ScenarioReport execute(Scenario scenario);
}
