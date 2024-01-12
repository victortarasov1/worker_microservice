package executor.service.queue.consumer.proxy

import executor.service.aop.logger.annotation.Logged
import executor.service.model.ProxyConfigHolder
import executor.service.queue.consumer.QueueExtractor
import org.springframework.stereotype.Component

@Component
@Logged
class ProxyConsumerImpl(
    private val extractor: QueueExtractor
) : ProxyConsumer {
    private val key = "proxy.queue.key"

    override fun poll() = extractor.poll(key, ProxyConfigHolder::class.java)

}