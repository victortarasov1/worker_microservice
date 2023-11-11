package executor.service.redis.queue.listener.scenario.dto

import executor.service.model.Scenario

data class ScenarioDto(val name: String, val site: String, val steps: List<StepDto>) {
    fun createScenario(): Scenario {
        val stepList = steps.map(StepDto::createStep)
        return Scenario(name, site, stepList)
    }
}