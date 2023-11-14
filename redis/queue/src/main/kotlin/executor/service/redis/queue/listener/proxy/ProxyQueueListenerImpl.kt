package executor.service.redis.queue.listener.proxy

import executor.service.aop.logger.annotation.Logged
import executor.service.model.ProxyConfigHolder
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
@Logged
class ProxyQueueListenerImpl(private val template: RedisTemplate<String, Any>) : ProxyQueueListener {
    private val key = "proxy.queue.key"

    override fun poll() = template.opsForList().rightPop(key) as? ProxyConfigHolder

}