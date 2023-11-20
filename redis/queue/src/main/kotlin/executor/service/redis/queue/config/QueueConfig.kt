package executor.service.redis.queue.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class QueueConfig(private val jedisConnectionFactory: JedisConnectionFactory) {
    @Bean
    fun redisTemplate() = StringRedisTemplate().apply { connectionFactory = jedisConnectionFactory }

    @Bean
    fun mapper() = ObjectMapper().apply { registerModule(JavaTimeModule()) }

}