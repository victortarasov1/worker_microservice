package executor.service.redis.queue.listener.scenario

import executor.service.model.Scenario
import executor.service.redis.queue.listener.scenario.dto.ScenarioDto
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ScenarioQueueListenerImpl(private val template: RedisTemplate<String, Any>) : ScenarioQueueListener {
    private val key = "scenario.queue.key"
    override fun poll(): List<Scenario> {
        val result = template.opsForList().rightPop(key)
        val dtoList = (result as? List<*>)?.map { it as ScenarioDto } ?: emptyList()
        return dtoList.map(ScenarioDto::createScenario)
    }
}