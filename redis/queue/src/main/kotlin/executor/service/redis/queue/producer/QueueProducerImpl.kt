package executor.service.redis.queue.producer

import executor.service.model.Scenario
import executor.service.redis.queue.producer.dto.ScenarioReportDto
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class QueueProducerImpl(private val template: RedisTemplate<String, Any>) : QueueProducer {
    private val key = "report.queue.key"
    override fun add(scenarios: List<Scenario>) {
        val dtoList = scenarios.map { ScenarioReportDto(it) }
        template.opsForList().leftPush(key, dtoList)
    }
}