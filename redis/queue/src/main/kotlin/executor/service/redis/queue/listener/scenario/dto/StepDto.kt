package executor.service.redis.queue.listener.scenario.dto

import executor.service.model.Step

data class StepDto(val action: String, val value: String) {
    fun createStep() = Step(action, value)
}
