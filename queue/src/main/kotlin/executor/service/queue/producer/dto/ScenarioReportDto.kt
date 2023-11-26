package executor.service.queue.producer.dto

import executor.service.model.Scenario
import java.time.LocalDateTime

data class ScenarioReportDto(
    val scenarioId: String,
    val name: String,
    val site: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val errorMessage: String?,
    val webDriverInfo: String,
    val stepReports: List<StepReportDto>
) {
    constructor(scenario: Scenario) : this(
        scenarioId = scenario.id,
        name = scenario.name,
        site = scenario.site,
        startTime = scenario.report.startTime,
        webDriverInfo = scenario.report.webDriverInfo,
        endTime = scenario.report.endTime,
        errorMessage = scenario.report.errorMessage,
        stepReports = scenario.steps.filter { it.report != null }.map { StepReportDto(it) }.toList()
    )
}
