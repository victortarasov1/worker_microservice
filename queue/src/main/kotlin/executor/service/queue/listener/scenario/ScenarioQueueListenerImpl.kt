package executor.service.queue.listener.scenario

import executor.service.aop.logger.annotation.Logged
import executor.service.model.Scenario
import executor.service.queue.listener.QueueExtractor
import org.springframework.stereotype.Component

@Component
@Logged
class ScenarioQueueListenerImpl(
    private val extractor: QueueExtractor
) : ScenarioQueueListener {
    private val key = "scenario.queue.key"
    override fun poll() = extractor.poll(key, Scenario::class.java)
}