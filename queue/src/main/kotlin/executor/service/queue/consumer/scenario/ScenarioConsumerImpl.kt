package executor.service.queue.consumer.scenario

import executor.service.aop.logger.annotation.Logged
import executor.service.model.Scenario
import executor.service.queue.consumer.QueueExtractor
import org.springframework.stereotype.Component

@Component
@Logged
internal class ScenarioConsumerImpl(
    private val extractor: QueueExtractor
) : ScenarioConsumer {
    private val key = "scenario.queue.key"
    override fun poll() = extractor.poll(key, Scenario::class.java)
}