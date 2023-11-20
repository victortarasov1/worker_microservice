package executor.service.redis.queue.listener.proxy

import executor.service.aop.logger.annotation.Logged
import executor.service.model.ProxyConfigHolder
import executor.service.redis.queue.listener.QueueExtractor
import org.springframework.stereotype.Component

@Component
@Logged
class ProxyQueueListenerImpl(
    private val extractor: QueueExtractor
) : ProxyQueueListener {
    private val key = "proxy.queue.key"

    override fun poll() = extractor.poll(key, ProxyConfigHolder::class.java)

}