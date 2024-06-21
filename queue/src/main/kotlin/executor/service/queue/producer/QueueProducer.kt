package executor.service.queue.producer

import executor.service.model.Scenario

interface QueueProducer {
    fun add(scenario: Scenario)
}