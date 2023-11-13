package executor.service.redis.queue.producer.dto

import executor.service.model.Scenario
import java.time.LocalDateTime


data class ScenarioReportDto(
    val name: String,
    val site: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val errorMessage: String,
    val webdriverInfo: String,
    val steps: List<StepReportDto>

) {
    constructor(scenario: Scenario) :
            this(
                scenario.name, scenario.site, scenario.report.startTime,
                scenario.report.endTime, scenario.report.errorMessage,
                scenario.report.webDriverInfo, scenario.steps.map { StepReportDto(it) }
            )
}