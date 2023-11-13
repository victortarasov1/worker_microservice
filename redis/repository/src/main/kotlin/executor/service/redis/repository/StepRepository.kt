package executor.service.redis.repository

import executor.service.model.Step
import org.springframework.data.repository.CrudRepository

interface StepRepository : CrudRepository<Step, String>