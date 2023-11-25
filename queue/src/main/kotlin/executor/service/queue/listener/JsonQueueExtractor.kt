package executor.service.queue.listener

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class JsonQueueExtractor(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : QueueExtractor {
    override fun <T> poll(key: String, clazz: Class<T>): T? {
        val data = template.opsForList().rightPop(key)
        return data?.let { mapper.readValue(it, clazz) }
    }
}