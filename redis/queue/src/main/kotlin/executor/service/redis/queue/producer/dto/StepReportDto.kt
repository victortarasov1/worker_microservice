package executor.service.redis.queue.producer.dto

import executor.service.model.Step
import java.time.LocalTime

data class StepReportDto(
    val action: String,
    val value: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val errorMessage: String
) {
    constructor(step: Step) : this(
        step.action, step.value,
        step.report.startTime, step.report.endTime,
        step.report.errorMessage
    )
}
