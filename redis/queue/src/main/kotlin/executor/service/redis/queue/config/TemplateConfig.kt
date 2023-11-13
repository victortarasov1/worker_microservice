package executor.service.redis.queue.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
class TemplateConfig(private val jedisConnectionFactory: JedisConnectionFactory) {
    @Bean
    fun redisTemplate() = RedisTemplate<String, Any>().apply{
        connectionFactory = jedisConnectionFactory
        valueSerializer = GenericJackson2JsonRedisSerializer()
    }

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        registerModule(JavaTimeModule())
    }
}