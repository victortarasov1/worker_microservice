package executor.service.redis.repository

import executor.service.model.Scenario
import org.springframework.data.repository.CrudRepository

interface ScenarioRepository : CrudRepository<Scenario, String>