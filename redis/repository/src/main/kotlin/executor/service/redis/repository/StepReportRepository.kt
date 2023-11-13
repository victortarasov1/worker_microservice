package executor.service.redis.repository

import executor.service.model.StepReport
import org.springframework.data.repository.CrudRepository

interface StepReportRepository : CrudRepository<StepReport, String>