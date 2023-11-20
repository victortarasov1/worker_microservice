package executor.service.redis.queue.producer

import com.fasterxml.jackson.databind.ObjectMapper
import executor.service.aop.logger.annotation.Logged
import executor.service.model.Scenario
import executor.service.redis.queue.producer.dto.ScenarioReportDto
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
@Logged
class QueueProducerImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : QueueProducer {
    private val key = "report.queue.key"
    override fun add(scenario: Scenario) {
        ScenarioReportDto(scenario)
            .also { println(it) }
            .let { mapper.writeValueAsString(it) }
            .run { template.opsForList().leftPush(key, this) }
    }
}