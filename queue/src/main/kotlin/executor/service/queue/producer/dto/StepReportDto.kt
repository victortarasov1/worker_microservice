package executor.service.queue.producer.dto

import executor.service.model.Step
import java.time.LocalTime

data class StepReportDto(
    val action: String,
    val value: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val errorMessage: String?
) {
    constructor(step: Step) : this(
        action = step.action,
        value = step.value,
        startTime = step.report?.startTime!!,
        endTime = step.report?.endTime!!,
        errorMessage = step.report?.errorMessage
    )
}
