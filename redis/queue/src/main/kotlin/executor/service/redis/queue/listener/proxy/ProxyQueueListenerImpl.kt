package executor.service.redis.queue.listener.proxy

import executor.service.model.ProxyConfigHolder
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ProxyQueueListenerImpl(private val template: RedisTemplate<String, Any>) : ProxyQueueListener {
    private val key = "proxy.queue.key"

    override fun poll(): ProxyConfigHolder? = template.opsForList().rightPop(key) as? ProxyConfigHolder

}