package executor.service.redis.queue.listener.scenario

import executor.service.model.Scenario
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ScenarioQueueListenerImpl(private val template: RedisTemplate<String, Any>) : ScenarioQueueListener {
    private val key = "scenario.queue.key"
    override fun poll(): List<Scenario> {
        val result = template.opsForList().rightPop(key)
        return (result as? List<*>)?.map { it as Scenario } ?: emptyList()
    }
}